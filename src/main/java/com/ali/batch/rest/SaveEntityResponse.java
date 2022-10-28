package com.ali.batch.rest;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SaveEntityResponse {

    private boolean success = true;
    private Long timestamp = Instant.now().getEpochSecond();
    private Long identityValue;
    private String message;

    public SaveEntityResponse(Long identityValue){
        this.identityValue = identityValue;
    }

}
