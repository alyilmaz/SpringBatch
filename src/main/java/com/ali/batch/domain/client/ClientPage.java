package com.ali.batch.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
public class ClientPage {
    private int pageNumber = 0;
    private int pageSize   = 5;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String  sortBy = "name";
}
