package com.ali.batch.dto.client;

import com.ali.batch.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO extends BaseDTO {

    private String name;
    private String url;
}
