package com.ali.batch.config;

import com.ali.batch.domain.client.Client;
import com.ali.batch.dto.client.ClientDTO;
import org.springframework.batch.item.ItemProcessor;

public class ClientProcessor implements ItemProcessor<Client, ClientDTO> {

    @Override
    public ClientDTO process(final Client client) throws Exception {
        final ClientDTO clientDTO = new ClientDTO(client.getName(), client.getUrl());
        return clientDTO;
    }

}