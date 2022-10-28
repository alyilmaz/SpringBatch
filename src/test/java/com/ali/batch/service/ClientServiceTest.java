package com.ali.batch.service;

import com.ali.batch.domain.client.Client;
import com.ali.batch.domain.client.ClientPage;
import com.ali.batch.domain.client.ClientSearchCriteria;
import com.ali.batch.exception.ValidationException;
import com.ali.batch.mapper.client.ClientMapper;
import com.ali.batch.repository.client.ClientRepository;
import com.ali.batch.service.impl.ClientServiceImpl;
import com.ali.batch.dto.PageableList;
import com.ali.batch.dto.client.ClientDTO;
import com.ali.batch.helper.ClientDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientServiceTest {

    ClientDataHelper clientDataHelper = new ClientDataHelper();
    ClientRepository clientRepositoryMock;
    ClientService clientService;

    @BeforeEach
    void setUpCommon() {
        clientRepositoryMock = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepositoryMock);
    }

    @Test
    void getOneTest(){
        Optional<Client> client = Optional.of(clientDataHelper.getClient(1L));
        when(clientRepositoryMock.findById(1L)).thenReturn(client);
        ClientDTO data = clientService.getOne(1L);
        assertEquals(client.get().getId(), data.getId());
        assertEquals(client.get().getName(),data.getName());
        assertEquals(client.get().getUrl(), data.getUrl());
    }

    @Test
    void getOneButDoesNotExistTest(){
        Optional<Client> client = Optional.of(clientDataHelper.getClient(1L));
        when(clientRepositoryMock.findById(1L)).thenReturn(client);
        Assertions.assertThrows(ValidationException.class, () ->{
            clientService.getOne(2L);
        },"Error: Not found!");
    }

    @Test
    void getAllPaginationTest(){
        Pageable pageable = clientDataHelper.getPageable(0, 1, "asc", "name");
        Page<Client> clients = clientDataHelper.getPageData(pageable,clientDataHelper.getClient(1L));
        when(clientRepositoryMock.getAllBySearch(any(ClientPage.class), any(ClientSearchCriteria.class))).thenReturn(clients);
        PageableList<ClientDTO> data = clientService.getAllByPagination(0, 1, "name", "asc", "name", "yil");
        assertEquals(clients.getTotalPages(), data.getTotalPages());
        assertEquals(clients.getTotalPages(), data.getTotalPages());
        assertEquals(clients.getContent().get(0).getName(), data.getData().get(0).getName());
    }

    @Test
    void getAllPaginationButTheNameDoesNotExistTest(){
        ClientPage clientPage = clientDataHelper.getClientPage(0, 1, "asc", "name");
        Pageable pageable = clientDataHelper.getPageable(0, 1, "asc", "name");
        Page<Client> clients = clientDataHelper.getPageData(pageable,clientDataHelper.getClient(1L));
        ClientSearchCriteria clientSearchCriteria = new ClientSearchCriteria("yil");
        when(clientRepositoryMock.getAllBySearch(clientPage, clientSearchCriteria)).thenReturn(clients);
        assertThrows(ValidationException.class, () ->{
            clientService.getAllByPagination(0, 1, "name", "asc", "name", "kuehne");
        }, "Error: Not Found!");
    }

    @Test
    void saveTest(){
        Client client = clientDataHelper.getClient(1L);
        when(clientRepositoryMock.save(client)).thenReturn(client);
        Long id = clientService.save(client.toDTO(ClientMapper.class));
        assertEquals(client.getId(), id);
    }

    @Test
    void updateTest(){
        Optional<Client> client = Optional.of(clientDataHelper.getClient(1L));
        when(clientRepositoryMock.findById(client.get().getId())).thenReturn(client);
        when(clientRepositoryMock.save(client.get())).thenReturn(client.get());
        ClientDTO data = clientService.update(client.get().toDTO(ClientMapper.class));
        assertEquals(client.get().getName(), data.getName());
    }

    @Test
    void updateButDoesNotExistTest(){
        Optional<Client> client = Optional.of(clientDataHelper.getClient(1L));
        Optional<Client> clientFake = Optional.of(clientDataHelper.getClient(2L));
        when(clientRepositoryMock.findById(client.get().getId())).thenReturn(client);
        when(clientRepositoryMock.save(client.get())).thenReturn(client.get());
        assertThrows(ValidationException.class, () ->{
            clientService.update(clientFake.get().toDTO(ClientMapper.class));
        },"Error: The client does not found!");
    }

    @Test
    void deleteTest(){
        Optional<Client> client = Optional.of(clientDataHelper.getClient(1L));
        when(clientRepositoryMock.findById(client.get().getId())).thenReturn(client);
        clientService.delete(1L);
        verify(clientRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void deleteButDoesNotExistTest(){
        Optional<Client> client = Optional.of(clientDataHelper.getClient(1L));
        when(clientRepositoryMock.findById(client.get().getId())).thenReturn(client);
        assertThrows(ValidationException.class, () ->{
            clientService.delete(2L);
        }, "Error: Not found!");

    }
}
