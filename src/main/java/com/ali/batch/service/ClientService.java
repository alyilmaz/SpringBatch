package com.ali.batch.service;

import com.ali.batch.dto.PageableList;
import com.ali.batch.dto.client.ClientDTO;

public interface ClientService {

    ClientDTO getOne(Long id);
    PageableList<ClientDTO> getAllByPagination(Integer pageNo, Integer pageSize, String sortBy, String sortDirection, String searchBy, String keyword);
    Long save(ClientDTO clientDTO);
    ClientDTO update(ClientDTO clientDTO);
    void delete(Long id);
}
