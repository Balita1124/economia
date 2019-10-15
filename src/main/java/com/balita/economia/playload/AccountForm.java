package com.balita.economia.playload;


import com.balita.economia.model.Account;
import com.balita.economia.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountForm {

    @NotBlank
    private String name;
    private String number;
    private String description;
    private Double amount;

    public Account buildAccount( Account account){
        account.setName(name);
        account.setNumber(number);
        account.setDescription(description);
        account.setAmount(amount);
        return account;
    }
}
