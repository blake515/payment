package org.demo.paymentdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private T data;
    private String state;
    private String message;
    private String error;

}
