package com.rawat.electrolok.store.dtos;


import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3,max = 25,message = "Invalid Name !!") // for Validation
    private String name;
    @Size(min=4,max=6,message = "Incorrect Gender")
    @NotBlank(message = "Gender can't be Blank!!")
    private String gender;
    //@Email(message = "Invalid Email!!") // Its not Perfect Email Validation cuz its allowing this-> adi@xyz
    @Pattern(regexp ="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Email Format is Invalid")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    @NotEmpty(message = "Invalid Password")
    private String password;
    @NotBlank(message = "About can't be Blank")
    private String about;
    private String imageName;

    // @Pattern
    // Custom validator
}
