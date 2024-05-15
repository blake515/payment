package org.demo.paymentdemo.controller;


import org.demo.paymentdemo.common.ApiResponse;
import org.demo.paymentdemo.dto.CardDto;
import org.demo.paymentdemo.entity.CardEntity;
import org.demo.paymentdemo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * 카드등록
     * @param cardDto
     * @return
     */
    @PostMapping("/card/addCard")
    public ResponseEntity<ApiResponse<CardEntity>> cardRegister(@RequestBody CardDto cardDto) {
        ApiResponse<CardEntity> response = cardService.addCard(cardDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
