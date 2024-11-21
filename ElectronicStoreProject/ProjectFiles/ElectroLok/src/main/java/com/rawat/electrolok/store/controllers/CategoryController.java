package com.rawat.electrolok.store.controllers;

import com.rawat.electrolok.store.dtos.*;
import com.rawat.electrolok.store.services.CategoryService;
import com.rawat.electrolok.store.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    //logger
    Logger logger  = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;
    private ModelMapper mapper;

    @Value("${category.profile.image.path}")
    private String categoryImageUploadPath;

    @Autowired
    private FileService fileService;

    // create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDtoCreated = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDtoCreated,HttpStatus.CREATED);
    }
    // update
    @PostMapping("{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @RequestBody CategoryDto categoryDto,
            @PathVariable String categoryId){
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    // delete
    @DeleteMapping("{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        ApiResponseMessage deleteResponse = ApiResponseMessage.builder().message("Category with ID: " + categoryId + " Deleted Succesfully.").httpStatus(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(deleteResponse,HttpStatus.OK);
    }

    // get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    // get single
    @GetMapping("/{categoryId}")
     ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        CategoryDto singleCategory = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(singleCategory,HttpStatus.ACCEPTED);
    }


    // upload Category Image

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        String imageName = fileService.uploadFile(image, categoryImageUploadPath);

        CategoryDto category = categoryService.getCategory(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categorydto = categoryService.update(category, categoryId);
//        logger.info("User Image Uploaded {}", userDto.toString());

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).httpStatus(HttpStatus.CREATED).message("This is a Category Image").build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    // Serve Category Image
    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response)throws Exception{
        CategoryDto category = categoryService.getCategory(categoryId);
        logger.info("Category Image Name: {}",category.getCoverImage());
        InputStream resource = fileService.getResource(categoryImageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
