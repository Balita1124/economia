package com.balita.economia.playload;

import com.balita.economia.model.Account;
import com.balita.economia.model.Partner;
import com.balita.economia.model.TransTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionForm {

    @NotNull
    private Partner partner;

    @NotNull
    private Account account;

    @NotNull
    private Double amount;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String remarks;

    @NotNull
    private TransTypeEnum transTypeEnum;
}
