package com.example;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PageModel<T> {

    private Integer totalPages;
    private Integer totalElements;
    private Integer pageNumber;
    private Integer size;
    private List<T> content;
}
