package org.demo.paymentdemo.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.demo.paymentdemo.common.ApiResponse;
import org.demo.paymentdemo.common.status.PaymentStatus;
import org.demo.paymentdemo.dto.PayDto;
import org.demo.paymentdemo.dto.PayResponseDto;
import org.demo.paymentdemo.entity.PayEntity;
import org.demo.paymentdemo.service.PayService;
import org.demo.paymentdemo.service.PaySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class PayController {

    private final PayService payService;

    @Autowired
    public PayController(PayService payService) {
        this.payService = payService;
    }

    /**
     * 결제요청 with CardRefId
     * @param payDto
     * @return
     */
    @PostMapping("/payment/request")
    public ResponseEntity<ApiResponse<PayEntity>> payRequest(@RequestBody PayDto payDto) {
        PayEntity pe = new PayEntity();
        pe.setToken(payDto.getToken());
        pe.setCardRefId(payDto.getCardRefId());
        pe.setPaymentStatus(PaymentStatus.PAYMENT_REQUESTED);

        ApiResponse<PayEntity> response = payService.payRequest(pe);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 결제요청 내용목록
     * @param paymentStatus
     * @param pageSize (옵션)
     * @param pageNumber (옵션)
     * @return
     */
//    @GetMapping("/payment/search")
//    public ResponseEntity<List<PayEntity>> searchPayments(@RequestParam(required = false) String paymentStatus,
//            @RequestParam(required = false) LocalDateTime cardNumber,
//            @RequestParam(required = false) LocalDateTime startDate,
//            @RequestParam(required = false) LocalDateTime endDate) {
//        List<PayEntity> payments = payService.searchList(paymentStatus);
//        return new ResponseEntity<>(payments, HttpStatus.OK);
//    }
    @PostMapping("/payment/search")
    public ResponseEntity<List<PayEntity>> searchPayments(@RequestBody PayDto payDto) {
        List<PayEntity> payments = payService.searchList(payDto);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
