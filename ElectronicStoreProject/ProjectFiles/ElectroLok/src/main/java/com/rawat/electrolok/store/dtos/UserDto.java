package com.rawat.electrolok.store.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    private String name;

    private String Gender;

    private String email;

    private String password;

    private String about;

    private String imageName;
}
