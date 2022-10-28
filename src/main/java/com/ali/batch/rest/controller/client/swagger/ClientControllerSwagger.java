package com.ali.batch.rest.controller.client.swagger;

import com.ali.batch.dto.PageableList;
import com.ali.batch.dto.client.ClientDTO;
import com.ali.batch.rest.DeleteRequest;
import com.ali.batch.rest.SaveEntityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Client API Service", description= "this API provides to CRUD operation on the DB")
public interface ClientControllerSwagger {

    @Operation(description = "get the data by id")
    ClientDTO getOne(Long id);

    @Operation(description = "get the list data with pageable")
    PageableList<ClientDTO> getAllByPagination(@Parameter(description = "Default parameter is 0") Integer pageNo,
                                               @Parameter(description = "Default parameter is 5") Integer pageSize,
                                               @Parameter(description = "Default parameter is name")
                                                       String sortBy,
                                               @Parameter(description = "Default parameter is asc")
                                                       String sortDirection,
                                               @Parameter(description = "Default parameter is empty") String keyword);
    @Operation(description = "save data in the DB")
    SaveEntityResponse save(ClientDTO clientDTO);

    @Operation(description = "update the exist data")
    SaveEntityResponse update(ClientDTO clientDTO);

    @Operation(description = "delete data with object which ccontains list of id")
    List<SaveEntityResponse> delete(@Parameter(description = "need id List to delete multiple trainings ") DeleteRequest id);
}
