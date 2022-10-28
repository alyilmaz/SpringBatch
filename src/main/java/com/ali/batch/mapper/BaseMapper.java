package com.ali.batch.mapper;


import com.ali.batch.domain.BaseEntity;
import com.ali.batch.dto.BaseDTO;

import java.util.List;

public interface BaseMapper<T extends BaseDTO, S extends BaseEntity> {

    T toDTO(S entity);

    List<T> toDTOList(List<S> dtoList);

    S toEntity(T dto);

}
