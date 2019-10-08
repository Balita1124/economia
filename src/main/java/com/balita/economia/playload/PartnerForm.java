package com.balita.economia.playload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerForm {
    @NotBlank
    private String firstname;
    @NotBlank
    private String Lastname;

    private String phone;
    @Email
    private String email;
    @NotBlank
    private String account;
}
