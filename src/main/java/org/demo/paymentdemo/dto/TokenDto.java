package org.demo.paymentdemo.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String cardRefId;
    private boolean tokenYn;
}
