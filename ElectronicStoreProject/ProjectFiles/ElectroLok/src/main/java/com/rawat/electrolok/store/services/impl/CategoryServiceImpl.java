package com.rawat.electrolok.store.services.impl;

import com.rawat.electrolok.store.Helper.helper;
import com.rawat.electrolok.store.dtos.CategoryDto;
import com.rawat.electrolok.store.dtos.PageableResponse;
import com.rawat.electrolok.store.entities.Category;
import com.rawat.electrolok.store.entities.User;
import com.rawat.electrolok.store.repositories.CategoryRepository;
import com.rawat.electrolok.store.repositories.UserRepository;
import com.rawat.electrolok.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    // to map dto to entity
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Value("${category.profile.image.path}")
    private String imagePath;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        // generating CategoryID <Randomly>
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        // find Category by ID
        Category categoryUpdate = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        // update Category details
        categoryUpdate.setTitle(categoryDto.getTitle());
        categoryUpdate.setDescription(categoryDto.getDescription());
        categoryUpdate.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(categoryUpdate);
        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category categoryToDelete = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not Found!!"));
        categoryRepository.delete(categoryToDelete);
        try{
            String pathToImage = imagePath + categoryToDelete.getCoverImage();
            Path path = Paths.get(pathToImage);
            Files.delete(path);
        }catch(Exception eeeee){
                logger.info("Category Image not Found at this Location {}",imagePath+categoryToDelete.getCoverImage());
        };
        logger.info("Deleted Category : {}", categoryId);
    }
    // Get Single Category
    @Override
    public CategoryDto getCategory(String categoryId) {
        Category singleCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not Found!!"));
        return mapper.map(singleCategory, CategoryDto.class);
    }
// get All Category
    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;

    }
}
