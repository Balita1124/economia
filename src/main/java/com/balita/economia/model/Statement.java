package com.balita.economia.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "statements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"account", "transaction"})
@ToString(exclude = {"account", "transaction"})
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;
    @NotNull
    private Double amount;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:MM:ss")
    private Date date;

    public Statement(String name, Account account, Transaction transaction, Double amount, Date date) {
        this.name = name;
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.date = date;
    }
}
