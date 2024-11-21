package com.rawat.electrolok.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "category_title",length = 100)
    private String title;
    @Column(name = "category_description",length = 200)
    private String description;
    @Column(name = "category_cover_image")
    private String coverImage;
}
