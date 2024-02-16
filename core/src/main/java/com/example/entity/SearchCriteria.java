package com.example.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SearchCriteria<T> {
    private String key;
    private char operation;
    private T content;
}
