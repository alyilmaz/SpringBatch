package com.ali.batch.repository;

import com.ali.batch.domain.client.Client;
import com.ali.batch.domain.client.ClientPage;
import com.ali.batch.domain.client.ClientSearchCriteria;
import org.springframework.data.domain.Page;

public interface PaginationRepository {

    Page<Client> getAllBySearch(ClientPage clientPage, ClientSearchCriteria clientSearchCriteria);
}
