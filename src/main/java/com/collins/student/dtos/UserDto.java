package com.collins.student.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    @NotEmpty(message = "First name field should not be empty or null!")
    private String firstName;
    @NotEmpty(message = "Last name field should not be empty or null!")
    private String lastName;
    @NotEmpty(message = "Email field should not be empty or null!")
    @Email(message = "The Email address should valid!")
    private String email;
}
