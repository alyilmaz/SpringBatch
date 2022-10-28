package com.ali.batch.service.impl;

import com.ali.batch.domain.client.Client;
import com.ali.batch.domain.client.ClientPage;
import com.ali.batch.domain.client.ClientSearchCriteria;
import com.ali.batch.dto.PageableList;
import com.ali.batch.dto.client.ClientDTO;
import com.ali.batch.exception.ValidationException;
import com.ali.batch.mapper.client.ClientMapper;
import com.ali.batch.repository.client.ClientRepository;
import com.ali.batch.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDTO getOne(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return client.get().toDTO(ClientMapper.class);
        }else{
            throw new ValidationException("Error: Not found!");
        }
    }

    @Override
    public PageableList<ClientDTO> getAllByPagination(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String searchBy, String keyword) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(sortDir)){
            sortDirection = Sort.Direction.DESC;
        }
        ClientPage clientPage = new ClientPage(pageNo, pageSize, sortDirection, sortBy);
        ClientSearchCriteria clientSearchCriteria = new ClientSearchCriteria(keyword);
        Page<Client> allBySearch = clientRepository.getAllBySearch(clientPage, clientSearchCriteria);
        if(allBySearch == null){
            throw new ValidationException("Error: Not Found!");
        }
        Page<ClientDTO> all = allBySearch.map(client -> client.toDTO(ClientMapper.class));
        return new PageableList<>(all);
    }

    @Override
    public Long save(ClientDTO clientDTO) {
        Client client = clientRepository.save(new Client().fromDTO(clientDTO, ClientMapper.class));
        return client.getId();
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {
        Optional<Client> client = clientRepository.findById(clientDTO.getId());
        if(client.isPresent()){
            Client updatedClient = clientRepository.save(new Client().fromDTO(clientDTO, ClientMapper.class));
            return updatedClient.toDTO(ClientMapper.class);
        }else{
            throw new ValidationException("Error: The client does not found!");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Client> training = clientRepository.findById(id);
        if (training.isPresent()) {
            clientRepository.deleteById(id);
        } else {
            throw new ValidationException("Error: Not found!");
        }
    }
}
