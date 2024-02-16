package com.example;

import com.example.dbentity.Inventory;
import com.example.entity.BaseApplication;
import com.example.entity.InventoryApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeanConfig {


    @Bean
    public ModelMapper getModelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(InventoryApplication.class, Inventory.class)
                .addMapping(BaseApplication::getCreatedAt,Inventory::setCreatedOn)
                        .addMapping(BaseApplication::getUpdatedAt,Inventory::setUpdatedOn);
        modelMapper.typeMap(Inventory.class,InventoryApplication.class).
                addMapping(Inventory::getCreatedOn,BaseApplication::setCreatedAt)
                .addMapping(Inventory::getUpdatedOn,BaseApplication::setUpdatedAt);
        return modelMapper;
    }

}
