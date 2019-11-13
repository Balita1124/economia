package com.balita.economia.playload;

import com.balita.economia.model.Account;
import com.balita.economia.model.Credit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditPayForm {
    private Double due;
    private Double left;
    private Double amount;
    private Account account;
}
