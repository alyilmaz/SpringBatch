package com.ali.batch.mapper.client;

import com.ali.batch.domain.client.Client;
import com.ali.batch.dto.client.ClientDTO;
import com.ali.batch.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper extends BaseMapper<ClientDTO, Client> {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
}
