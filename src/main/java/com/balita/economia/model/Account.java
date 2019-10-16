package com.balita.economia.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude="user")
@ToString(exclude="user")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String number;
    private String description;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
