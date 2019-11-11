package com.balita.economia.playload;

import lombok.*;
import com.balita.economia.model.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditForm {

    private Partner partner;

    private Account account;

    @NotNull
    private TransTypeEnum transTypeEnum;

    @NotNull
    private Double amount;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:MM:ss")
    private Date date;

    private String remarks;
}
