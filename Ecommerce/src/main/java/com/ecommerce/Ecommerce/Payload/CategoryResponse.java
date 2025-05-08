package com.ecommerce.Ecommerce.Payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> categoryDTOList;

    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private boolean lastPage;
}
