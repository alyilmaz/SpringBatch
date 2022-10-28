package com.ali.batch.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteRequest {
    List<Long> id;
}
