package com.ali.batch.rest.controller.client;

import com.ali.batch.service.ClientService;
import com.ali.batch.dto.PageableList;
import com.ali.batch.dto.client.ClientDTO;
import com.ali.batch.rest.DeleteRequest;
import com.ali.batch.rest.SaveEntityResponse;
import com.ali.batch.rest.controller.client.swagger.ClientControllerSwagger;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController implements ClientControllerSwagger {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public ClientDTO getOne(Long id) {
        return clientService.getOne(id);
    }

    @GetMapping
    public PageableList<ClientDTO> getAllByPagination(@RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                                      @RequestParam(defaultValue = "name") String sortBy,
                                                      @RequestParam(defaultValue = "asc") String sortDirection,
                                                      @RequestParam(defaultValue = "") String keyword) {
        return clientService.getAllByPagination(pageNo,pageSize,sortBy,sortDirection,"name", keyword);
    }

    @PostMapping
    public SaveEntityResponse save(@RequestBody ClientDTO clientDTO) {
        return new SaveEntityResponse(clientService.save(clientDTO));
    }

    @PutMapping
    public SaveEntityResponse update(@RequestBody ClientDTO clientDTO) {
        return new SaveEntityResponse(clientService.update(clientDTO).getId());
    }

    @DeleteMapping("/delete")
    public List<SaveEntityResponse> delete(@RequestBody DeleteRequest ids) {
        List<SaveEntityResponse> responses = new ArrayList<>();
        ids.getId().forEach(id -> {
            clientService.delete(id);
            responses.add(new SaveEntityResponse(id));
        });
        return responses;
    }
}
