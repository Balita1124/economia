package com.balita.economia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
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
    private TransTypeEnum transTypeEnum;

    @NotNull
    private Double amount;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:MM:ss")
    private Date date;

    private String remarks;

    public Credit(Partner partner, Account account, @NotNull TransTypeEnum transTypeEnum, @NotNull Double amount, @NotNull Date date, String remarks) {
        this.partner = partner;
        this.partnerName = partner.getFirstname() + " " + partner.getLastname();
        this.account = account;
        this.transTypeEnum = transTypeEnum;
        this.amount = amount;
        this.date = date;
        this.remarks = remarks;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
        this.partnerName = partner.getFirstname() + " " + partner.getLastname();
    }
}
