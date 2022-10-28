package com.ali.batch.domain.client;

import com.ali.batch.domain.BaseEntity;
import com.ali.batch.dto.client.ClientDTO;
import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client extends BaseEntity<ClientDTO, Client> {

    private String name;
    private String url;

}
