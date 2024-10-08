package com.rawat.electrolok.store.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Products {

    @Id
    private String product_id;

    private String product_name;

    private String product_description;

    private boolean product_availability;

    private int product_rating;

    private double product_price;

    private String product_image;



}
