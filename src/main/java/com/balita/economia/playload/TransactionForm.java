package com.balita.economia.playload;

import com.balita.economia.model.Account;
import com.balita.economia.model.Partner;
import com.balita.economia.model.TransTypeEnum;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TransactionForm {


    private Partner partner;

    @NotNull
    private String partnerName;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @NotNull
    private Double amount;

    @NotNull
    private Date date;

    private String remarks;

    @NotNull
    private TransTypeEnum transTypeEnum;
}
