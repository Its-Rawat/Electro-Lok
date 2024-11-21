package com.rawat.electrolok.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @NotBlank(message = "Title cant be blank")
    @Size(min=5,message = "Title must be of minimum 5 Characters")
    private String title;

    @NotBlank(message = "Description Required !!")
    private String description;

    @NotBlank(message = "Cover Image Required !!")
    private String coverImage;
}
