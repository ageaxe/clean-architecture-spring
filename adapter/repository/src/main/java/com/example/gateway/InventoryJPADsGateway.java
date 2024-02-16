package com.example.gateway;

import com.example.dbentity.Inventory;
import com.example.entity.InventoryApplication;
import com.example.entity.Page;
import com.example.entity.User;
import com.example.search.util.CompositeSearchSpecification;
import com.example.search.util.SearchCriteria;
import com.example.search.util.SearchSpecification;
import com.example.usercase.exception.ResourceNotFoundException;
import com.example.usercase.port.InventoryDsGateway;
import com.example.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
public class InventoryJPADsGateway implements InventoryDsGateway {
    @Autowired
    private InventoryJPARepository repository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<InventoryApplication> getApplication(List<com.example.entity.SearchCriteria<String>> searchCriteria, PageCriteria pageCriteria, User user) {
        Pageable pageable = PageRequest.of(pageCriteria.getPageNumber(),pageCriteria.getSize());

        org.springframework.data.domain.Page<Inventory> result;
        if (searchCriteria.size() == 0 && user.getIsAdmin() == true) {
            //If User is admin and no filters applied
            result = repository.findAll(pageable);
        } else if (searchCriteria.size() == 0 && user.getIsAdmin() == false) {
            //If User is not admin and no filters applied
            SearchSpecification<Inventory> spec1 = new SearchSpecification<>(
                    this.getRBACSearchCriteria(user), SearchSpecification.JoinCondiion.OR);
            result = repository.findAll(spec1, pageable);
        } else if (searchCriteria.size() > 0 && user.getIsAdmin() == true) {
            List<SearchCriteria> searchCriteriaList = searchCriteria.stream().map(
                            criteria -> new SearchCriteria(criteria.getKey(), SearchCriteria.getSimpleOperation(criteria.getOperation()), criteria.getContent()))
                    .collect(Collectors.toList());
            SearchSpecification<Inventory> userFilterSpec = new SearchSpecification<>(
                    searchCriteriaList.toArray(new SearchCriteria[0]), SearchSpecification.JoinCondiion.AND);
            result = repository.findAll(userFilterSpec, pageable);
        } else {
            List<SearchCriteria> searchCriteriaList = searchCriteria.stream().map(
                            criteria -> new SearchCriteria(criteria.getKey(), SearchCriteria.getSimpleOperation(criteria.getOperation()), criteria.getContent()))
                    .collect(Collectors.toList());

            SearchSpecification<Inventory> userFilterSpec = new SearchSpecification<>(
                    searchCriteriaList.toArray(new SearchCriteria[0]), SearchSpecification.JoinCondiion.AND);

            SearchSpecification<Inventory> rbacSpec = new SearchSpecification<>(
                    this.getRBACSearchCriteria(user), SearchSpecification.JoinCondiion.OR);

            CompositeSearchSpecification<Inventory> compositeSearchSpec = new CompositeSearchSpecification<Inventory>(userFilterSpec, rbacSpec, CompositeSearchSpecification.JoinCondiion.AND);
            result = repository.findAll(compositeSearchSpec, pageable);
        }


        return Page.<InventoryApplication>builder()
                .size(result.getSize())
                .pageNumber(result.getNumber())
                .totalPages(result.getTotalPages())
                .totalElements(result.getNumberOfElements())
                .content(result.getContent().stream().map(obj -> modelMapper.map(obj, InventoryApplication.class)).collect(Collectors.toList()))
                .build();
    }

    @Override
    public InventoryApplication save(InventoryApplication application) {

        Inventory  inventory = modelMapper.map(application, Inventory.class);

        return modelMapper.map(
                repository.save(inventory),
                InventoryApplication.class);
    }

    @Override
    public void delete(String appID) {
        try {
            repository.deleteById(UUID.fromString(appID));
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException(MessageFormat.format("Application :{0} not found", appID));
        }
    }

    @Override
    public InventoryApplication getApplication(String appID, User user) {

        try {
            Optional<Inventory> appOption = null;

            if (user.getIsAdmin()) {
                appOption = repository.findById(UUID.fromString(appID));
            } else {
                SearchSpecification<Inventory> rbacSpec = new SearchSpecification<>(
                        this.getRBACSearchCriteria(user), SearchSpecification.JoinCondiion.OR);

                SearchCriteria idSearchCriteria = new SearchCriteria("id", SearchCriteria.Operation.EQUAL, UUID.fromString(appID));

                CompositeSearchSpecification<Inventory> compositeSearchSpec = new CompositeSearchSpecification<Inventory>(new SearchSpecification<Inventory>(idSearchCriteria), rbacSpec, CompositeSearchSpecification.JoinCondiion.AND);
                appOption = repository.findOne(compositeSearchSpec);
            }
            if (appOption.isPresent()) {
                log.trace("Retrieved DB App {} of type {}", appOption.get(), appOption.get().getClass());

                return modelMapper.map(appOption.get(),InventoryApplication.class);

            } else {
                throw new ResourceNotFoundException(MessageFormat.format("Application :{0} not found", appID));
            }

        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException for app ID {} ", appID, ex);
            throw new ResourceNotFoundException(MessageFormat.format("Application :{0} not found", appID));
        }
    }


    @Override
    public InventoryApplication getApplication(String appID) {

        Optional<Inventory> appOption = repository.findById(UUID.fromString(appID));

        if (appOption.isPresent()) {
            log.trace("Retrieved DB App {} of type {}", appOption.get(), appOption.get().getClass());

            return modelMapper.map(appOption.get(), InventoryApplication.class);

        } else {
            throw new ResourceNotFoundException(MessageFormat.format("Application :{0} not found", appID));
        }

    }


    private SearchCriteria[] getRBACSearchCriteria(User user) {
        SearchCriteria userSearchCriteria = new SearchCriteria("ownerUserList", SearchCriteria.Operation.ARRAY_ANY,
                user.getId());

        List<SearchCriteria> groupSearchCriteriaList = Arrays.stream(user.getGroups())
                .map(group -> new SearchCriteria("ownerGroupList", SearchCriteria.Operation.ARRAY_ANY, group))
                .collect(Collectors.toList());


        groupSearchCriteriaList.add(userSearchCriteria);
        return groupSearchCriteriaList.toArray(new SearchCriteria[0]);
    }
}
