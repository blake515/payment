package org.demo.paymentdemo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PayResponseDto {

    private Long id;
    private String cardRefId;
//    private String token;
    private LocalDateTime tokenExpiry;
    private String paymentStatus;

    private int pageNumber;
    private int pageSize;
}
