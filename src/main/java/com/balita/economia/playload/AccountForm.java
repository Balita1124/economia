package com.balita.economia.playload;


import com.balita.economia.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountForm {

    @NotBlank
    private String name;
    private String number;
    private String description;
    private Double amount;
}
