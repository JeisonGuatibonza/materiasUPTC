package com.uptc.edu.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO {

    private int pageNumber;
    private int pageSize;
    private long totalRecords;
    private int totalPages;
}