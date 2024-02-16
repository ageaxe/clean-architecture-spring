package com.example.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PageCriteria {
    private Integer pageNumber;
    private Integer size;
}
