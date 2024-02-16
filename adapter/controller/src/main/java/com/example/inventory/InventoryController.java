package com.example.inventory;

import com.example.entity.InventoryApplication;
import com.example.entity.Page;
import com.example.entity.PageCriteria;
import com.example.entity.SearchCriteria;
import com.example.BaseApplicationModel;
import com.example.PageModel;
import com.example.usercase.exception.ForbiddenException;
import com.example.usercase.exception.ResourceNotFoundException;
import com.example.usercase.inventory.AddOwnerToApplication;
import com.example.usercase.inventory.DeleteApplication;
import com.example.usercase.inventory.ListApplications;
import com.example.usercase.inventory.SaveApplication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin(origins = "#{'${cors.allow-origin}'.split(',')}", maxAge = 3600)

@RestController
@RequestMapping("/v1/data")
public class InventoryController extends BaseApplicationModel {

    @Autowired
    private DeleteApplication deleteApplication;

    @Autowired
    private ListApplications listApplications;

    @Autowired
    private SaveApplication saveApplication;

    @Autowired
    private AddOwnerToApplication addOwnerToApplication;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public PageModel<InventoryModel> getAllInventory(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false, value = "search") String search,
            @RequestHeader("auth_user") String authUser

    ) {
        try {
            List<SearchCriteria<String>> params = new ArrayList<>();
            if (search != null) {
                Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
                Matcher matcher = pattern.matcher(search + ",");
                while (matcher.find()) {
                    params.add(new SearchCriteria(matcher.group(1), matcher.group(2).charAt(0), matcher.group(3)));
                }
            }
            Page<InventoryApplication> useCaseResponse = listApplications.listApplication(params,
                    PageCriteria.builder().size(size).pageNumber(page).build(), authUser);

            List<InventoryModel> list = useCaseResponse.getContent().stream()
                    .map(obj -> modelMapper.map(obj, InventoryModel.class)).collect(Collectors.toList());
            return PageModel.<InventoryModel>builder()
                    .size(useCaseResponse.getSize())
                    .totalElements(useCaseResponse.getTotalElements())
                    .totalPages(useCaseResponse.getTotalPages())
                    .pageNumber(useCaseResponse.getPageNumber())
                    .content(list)
                    .build();
        } catch (ResourceNotFoundException | ForbiddenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Application found for user", exc);
        }
    }

    @GetMapping("/{id}")
    public InventoryModel get(@PathVariable("id") String id, @RequestHeader("auth_user") String authUser) {
        try {
            return modelMapper.map(
                    this.listApplications.getApplicationById(id, authUser),
                    InventoryModel.class);
        } catch (ResourceNotFoundException | ForbiddenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User is not authorized to perform this operation", exc);
        }
    }

    @PostMapping
    public InventoryModel create(@RequestBody InventoryModel inventory, @RequestHeader("auth_user") String authUser) {
        try {
            return modelMapper.map(
                    this.saveApplication.createApplication(
                            modelMapper.map(inventory, InventoryApplication.class),
                            authUser),
                    InventoryModel.class);
        } catch (ResourceNotFoundException | ForbiddenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong ,please try after some time", exc);
        }

    }

    @PutMapping("/{id}")
    public InventoryModel update(@PathVariable("id") String id,
            @RequestBody InventoryModel inventory,
            @RequestHeader("auth_user") String authUser) {
        try {
            inventory.setId(UUID.fromString(id));
            return modelMapper.map(
                    this.saveApplication.updateApplication(
                            modelMapper.map(inventory, InventoryApplication.class),
                            authUser),
                    InventoryModel.class);
        } catch (ResourceNotFoundException | ForbiddenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User is not authorized to perform this operation", exc);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id, @RequestHeader("auth_user") String authUser) {
        try {
            this.deleteApplication.deleteApplication(id, authUser);
            return ResponseEntity.accepted().body("Application deleted");
        } catch (ResourceNotFoundException | ForbiddenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "User is not authorized to perform this operation", exc);
        }
    }

    record ClientIdentifier(String clientIdentifier) {
    }

    @PostMapping("/addOwner")
    public InventoryModel addOwner(@RequestBody ClientIdentifier inventory,
            @RequestHeader("auth_user") String authUser) {
        try {
            return modelMapper.map(
                    this.addOwnerToApplication.addOwner(inventory.clientIdentifier, authUser),
                    InventoryModel.class);

        } catch (ResourceNotFoundException | ForbiddenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, exc.getMessage(), exc);
        }
    }

}
