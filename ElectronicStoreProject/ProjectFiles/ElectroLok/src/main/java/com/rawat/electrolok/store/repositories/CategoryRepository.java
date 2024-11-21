package com.rawat.electrolok.store.repositories;

import com.rawat.electrolok.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
