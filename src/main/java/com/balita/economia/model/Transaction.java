package com.balita.economia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
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

    public Transaction(Partner partner, Account account,Double amount,Date date, String remarks,TransTypeEnum transTypeEnum) {
        this.partner = partner;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
        this.transTypeEnum = transTypeEnum;
        this.partnerName = partner.getFirstname() + " " + partner.getLastname();
    }


    public void setPartner(Partner partner) {
        this.partner = partner;
        this.partnerName = partner.getFirstname() + " " + partner.getLastname();
    }
}
