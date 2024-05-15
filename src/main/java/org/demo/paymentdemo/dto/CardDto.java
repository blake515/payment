package org.demo.paymentdemo.dto;

import java.util.Date;
import lombok.Data;
import lombok.Getter;

@Data
public class CardDto {
    private String cardNumber;
    private String cardRefId;
    private String owner;
    private Date expirationDate;
}
