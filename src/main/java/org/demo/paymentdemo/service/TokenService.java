package org.demo.paymentdemo.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.demo.paymentdemo.common.ApiResponse;
import org.demo.paymentdemo.common.status.CardStatus;
import org.demo.paymentdemo.common.status.ResponseStatus;
import org.demo.paymentdemo.dto.TokenDto;
import org.demo.paymentdemo.entity.CardEntity;
import org.demo.paymentdemo.entity.TokenEntity;
import org.demo.paymentdemo.repository.CardRepository;
import org.demo.paymentdemo.repository.TokenRepository;
import org.demo.paymentdemo.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final CardRepository cardRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository, CardRepository cardRepository) {
        this.tokenRepository = tokenRepository;
        this.cardRepository = cardRepository;
    }

    public ApiResponse<TokenEntity> createToken(TokenDto tokenDto) {

        ApiResponse<TokenEntity> response = new ApiResponse<>();

        // null 체크
        if (StringUtils.isBlank(tokenDto.getCardRefId())) {
            response.setState(String.valueOf(ResponseStatus.FAIL));
            response.setMessage(String.valueOf(CardStatus.CARD_REF_ID_NULL));
            return response;
        }
        // card_ref_id로 CardEntity를 조회
        Optional<CardEntity> cardEntityOptional = cardRepository.findByCardRefId(tokenDto.getCardRefId());
        if (cardEntityOptional.isEmpty()) {
            response.setState(String.valueOf(ResponseStatus.FAIL));
            response.setMessage(String.valueOf(CardStatus.CARD_REF_ID_NOT_FOUND));
            return response;
        }

        TokenEntity te = new TokenEntity();
        log.info("cardEntityOptional.get(){}",cardEntityOptional.get());
        te.setCardRefIdFromCardEntity(cardEntityOptional.get());
        te.setExpirationDate(LocalDateTime.now().plusMinutes(1));
        te.setToken(TokenGenerator.generateToken());
//        토큰 생성만 하고 아직 미 사용.
        te.setTokenYn(Boolean.FALSE); // T : 1 , F : 0
        // 응답 상태 설정
        TokenEntity tokenEntity = tokenRepository.save(te);
        response.setState(String.valueOf(ResponseStatus.SUCCESS));
        // 데이터 설정
        response.setData(tokenEntity);
        return response;
    }
}
