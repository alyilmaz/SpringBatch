package com.ali.batch.helper;

import com.ali.batch.domain.client.Client;

public class ClientDataHelper extends PageableDataHelper<Client>{

    public Client getClient(Long id){
        Client client = new Client("Ali YILMAZ","google.com.tr");
        client.setId(id);
        return client;
    }
}
