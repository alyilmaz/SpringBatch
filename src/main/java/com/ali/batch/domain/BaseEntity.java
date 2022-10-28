package com.ali.batch.domain;


import com.ali.batch.mapper.BaseMapper;
import com.ali.batch.dto.BaseDTO;
import lombok.Data;
import org.mapstruct.factory.Mappers;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

@Data
@MappedSuperclass
public abstract class BaseEntity<T extends BaseDTO, S extends BaseEntity<T, S>> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public static <T extends BaseDTO, S extends BaseEntity> List<T> toDTOList(List<S> entityList,
                                                                              Class<? extends BaseMapper<T, S>> clazz) {
        return Mappers.getMapper(clazz).toDTOList(entityList);
    }

    public T toDTO(Class<? extends BaseMapper<T, S>> clazz) {
        return Mappers.getMapper(clazz).toDTO((S) this);
    }

    public S fromDTO(T dto, Class<? extends BaseMapper<T, S>> clazz) {
        return Mappers.getMapper(clazz).toEntity(dto);
    }
}
