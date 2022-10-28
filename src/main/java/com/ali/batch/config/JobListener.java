package com.ali.batch.config;

import com.ali.batch.dto.client.ClientDTO;
import com.ali.batch.dto.job.JobDTO;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobListener extends JobExecutionListenerSupport {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("The job is completed now!");
            List<ClientDTO> results = jdbcTemplate.query("SELECT name,url FROM client",
                    (rs,rowNum)->{
                        return new ClientDTO(rs.getString(1), rs.getString(2));
                    }
            );
            results.forEach(result -> {
                System.out.println("Client name: "+ result.getName());
            });
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution){
        String jobName = jobExecution.getJobInstance().getJobName();
        List<JobDTO> jobDTOList = jdbcTemplate.query("select batch_job_instance.job_name,batch_job_execution.status from batch_job_execution INNER JOIN batch_job_instance ON batch_job_execution.job_execution_id=batch_job_instance.job_instance_id ",
                (rs, rowNum) -> {
                                    return new JobDTO(rs.getString(1), rs.getString(2));
                                });
        List<JobDTO> results = jobDTOList.stream().filter(jobDTO -> jobName.equals(jobDTO.getJobName())).filter(jobDTO -> "COMPLETED".equals(jobDTO.getJobStatus())).collect(Collectors.toList());
        if(results.size() > 0){
            System.out.println("The job that named is " + jobName + " has already run so this job is being halted now!");
            jobExecution.stop();
        }

    }
}
