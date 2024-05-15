package org.demo.paymentdemo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PayDto {

    private Long id;
    private String cardRefId;
    private String token;
    private LocalDateTime tokenExpiry;
    private String paymentStatus;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int pageNumber;
    private int pageSize;
}
