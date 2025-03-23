package com.EcoMentor_backend.EcoMentor.User.useCases.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDTO {
    //Subject to change
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;
}
