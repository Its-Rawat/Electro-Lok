package com.rawat.electrolok.store.entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email",unique = true)
    private String Gender;
    private String email;
    @Column(name = "user_password",length = 10)
    private String password;
    @Column(name = "user_about",length = 1000)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;
}
