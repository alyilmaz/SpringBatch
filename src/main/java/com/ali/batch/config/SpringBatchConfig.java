package com.ali.batch.config;


import com.ali.batch.domain.client.Client;
import com.ali.batch.dto.client.ClientDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;


    @Bean
    public FlatFileItemReader<Client> reader() {
        FlatFileItemReader<Client> reader = new FlatFileItemReader<Client>();
        reader.setResource(new ClassPathResource("people.csv"));
        //the first line from the csv file should be ignored because this is HEAD line
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Client>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name", "url", ""});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(Client.class);
            }});
        }});
        return reader;
    }


    @Bean
    public ClientProcessor processor() {
        return new ClientProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ClientDTO> writer() {
        JdbcBatchItemWriter<ClientDTO> writer = new JdbcBatchItemWriter<ClientDTO>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO client (name,url) " +
                "VALUES (:name, :url)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job importUserJob(JobListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Client, ClientDTO> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
