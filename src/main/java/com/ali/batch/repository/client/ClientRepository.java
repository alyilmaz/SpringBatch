package com.ali.batch.repository.client;

import com.ali.batch.domain.client.Client;
import com.ali.batch.repository.PaginationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PaginationRepository, JpaRepository<Client, Long> {

}
