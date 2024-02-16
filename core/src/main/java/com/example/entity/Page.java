package com.example.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Page<T> {

    @NonNull
    private Integer totalPages;

    @NonNull
    private Integer totalElements;

    @NonNull
    private Integer pageNumber;

    @NonNull
    private Integer size;

    @NonNull
    private List<T> content;
}
