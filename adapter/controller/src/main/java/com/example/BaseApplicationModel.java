package com.example;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseApplicationModel {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
}
