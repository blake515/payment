package org.demo.paymentdemo.service;

import static org.demo.paymentdemo.utils.Validation.isValidCardNumber;

import java.time.LocalDate;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.demo.paymentdemo.common.ApiResponse;
import org.demo.paymentdemo.common.status.CardStatus;
import org.demo.paymentdemo.common.status.ResponseStatus;
import org.demo.paymentdemo.dto.CardDto;
import org.demo.paymentdemo.entity.CardEntity;
import org.demo.paymentdemo.repository.CardRepository;
import org.demo.paymentdemo.utils.EncryptionUtil;
import org.demo.paymentdemo.utils.NumberGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ApiResponse<CardEntity> addCard(CardDto cardDto) {

        ApiResponse<CardEntity> response = new ApiResponse<>();
        // 카드 null 여부 체크
        if(StringUtils.isBlank(cardDto.getCardNumber())){
            response.setMessage("card number is null");
            return response;
        }
        // 카드 번호 유효성 검사(숫자, 자리수)
        if (!isValidCardNumber(cardDto.getCardNumber())) {
            response.setState(String.valueOf(ResponseStatus.FAIL));
            response.setMessage(String.valueOf(CardStatus.INVALID_CARD_NUMBER));
            return response;
        }

        String reqCardNumber = cardDto.getCardNumber();
        log.info("암호화 하기전 평문 ======={}",reqCardNumber);
        String encCardNumber = EncryptionUtil.encrypt(reqCardNumber);
        log.info("enccardNumber ======{}",encCardNumber);
        // 이미 등록된 카드 인지 확인
        Optional<CardEntity> optionalCardEntity = cardRepository.findByCardNumber(
                encCardNumber);
        if (optionalCardEntity.isPresent()) {
            response.setState(String.valueOf(ResponseStatus.FAIL));
            response.setMessage(String.valueOf(CardStatus.DUPLICATED_CARD_NUMBER));
            return response;
        }

        // 새로운 카드 엔티티 생성 및 저장
        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardNumber(encCardNumber);
        cardEntity.setCardRefId(NumberGenerator.generateCardRefId());
        cardEntity.setOwner(cardDto.getOwner());
        cardEntity.setExpirationDate(LocalDate.now());

        // 카드 엔티티 저장
        CardEntity savedCard = cardRepository.save(cardEntity);
        //응답된 카드번호를 확인 위해서 일단 다시 복호화 하여 세팅! 필요시 제거.
        String dec_cardNumber = EncryptionUtil.decrypt(savedCard.getCardNumber());
        log.info("dec_cardNumber====={}",dec_cardNumber);
        savedCard.setCardNumber(dec_cardNumber);
        response.setState(String.valueOf(ResponseStatus.SUCCESS));
        response.setData(savedCard);
        return response;
    }
}
