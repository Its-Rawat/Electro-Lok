package com.rawat.electrolok.store.services;

import com.rawat.electrolok.store.dtos.CategoryDto;
import com.rawat.electrolok.store.dtos.PageableResponse;

public interface CategoryService {
    // create
    CategoryDto create(CategoryDto categoryDto);
    // update
    CategoryDto update(CategoryDto categoryDto, String categoryId);
    // delete
    void delete(String categoryId);
    // get
    CategoryDto getCategory(String categoryId);
    // getAll
    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);

}
