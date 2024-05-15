package org.demo.paymentdemo.controller;

import org.demo.paymentdemo.common.ApiResponse;
import org.demo.paymentdemo.dto.TokenDto;
import org.demo.paymentdemo.entity.TokenEntity;
import org.demo.paymentdemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 토큰 요청 with CardRefId
     * @param tokenDto
     * @return
     */
    @PostMapping("/token/get")
    public ResponseEntity<ApiResponse<TokenEntity>> createToken(@RequestBody TokenDto tokenDto) {
        ApiResponse<TokenEntity> response = tokenService.createToken(tokenDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
