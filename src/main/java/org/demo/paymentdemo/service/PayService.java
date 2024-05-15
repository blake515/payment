package org.demo.paymentdemo.service;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.demo.paymentdemo.common.ApiResponse;
import org.demo.paymentdemo.common.status.CardStatus;
import org.demo.paymentdemo.common.status.PaymentStatus;
import org.demo.paymentdemo.common.status.ResponseStatus;
import org.demo.paymentdemo.common.status.TokenStatus;
import org.demo.paymentdemo.dto.PayDto;
import org.demo.paymentdemo.entity.CardEntity;
import org.demo.paymentdemo.entity.PayEntity;
import org.demo.paymentdemo.entity.TokenEntity;
import org.demo.paymentdemo.repository.CardRepository;
import org.demo.paymentdemo.repository.PayRepository;
import org.demo.paymentdemo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class PayService {

    private final PayRepository payRepository;
    private final CardRepository cardRepository;
    private final TokenRepository tokenRepository;
    private final PayValidationService payValidationService;

    @Autowired
    public PayService(PayRepository payRepository, CardRepository cardRepository,
            TokenRepository tokenRepository, PayValidationService payValidationService) {
        this.payRepository = payRepository;
        this.cardRepository = cardRepository;
        this.tokenRepository = tokenRepository;
        this.payValidationService = payValidationService;
    }

    public ApiResponse<PayEntity> payRequest(PayEntity payEntity) {

        ApiResponse<PayEntity> response = new ApiResponse<>();
        if (StringUtils.isBlank(payEntity.getToken())) {
            response.setMessage("token is null!!!!");
            return response;
        }
        if (StringUtils.isBlank(payEntity.getCardRefId())) {
            response.setMessage("cardRefId is null!!!");
        }
        // reids 에서 token 조회 중복요청방지용도.
        String check_token = payEntity.getToken();
        String check_cardRefId = payEntity.getCardRefId();
        if (payValidationService.isDuplicatedOrder(check_token)) {
            response.setMessage(String.valueOf(ResponseStatus.REQUEST_IS_DUPLICATED));
            return response;
        }
        // token 없으면 redis 저장.
        payValidationService.saveToken(check_token, check_cardRefId);

        PayEntity pe = new PayEntity();
        // 카드 및 토큰 유효성 체크
        Optional<CardEntity> optionalCardEntity = cardRepository.findByCardRefId(
                payEntity.getCardRefId());
        if (optionalCardEntity.isEmpty()) {
            response.setMessage(String.valueOf(CardStatus.CARD_REF_ID_NOT_FOUND));
            response.setState(String.valueOf(ResponseStatus.FAIL));
            return response;
        }

        Optional<TokenEntity> optionalTokenEntity = tokenRepository.findByToken(
                payEntity.getToken());
        if (optionalTokenEntity.isEmpty()) {
            response.setState(String.valueOf(ResponseStatus.FAIL));
            response.setMessage(String.valueOf(TokenStatus.INVALID_TOKEN));
            return response;
        }
        // toke 만료시간 1분 , 사용여부 true이면 token 무효한 토큰으로 간주.
        log.info("토큰 사용여부================={}", optionalTokenEntity.get().isTokenYn());
        log.info("토큰 만료여부================={}", optionalTokenEntity.get().getExpirationDate()
                .isBefore(LocalDateTime.now().minusMinutes(1)));
        if (optionalTokenEntity.get().isTokenYn() ||
                optionalTokenEntity.get().getExpirationDate()
                        .isBefore(LocalDateTime.now().minusMinutes(1))
        ) {

            log.info("유효시간이 지났거나,사용된 토큰입니다.");
            response.setState(String.valueOf(ResponseStatus.FAIL));
            response.setMessage(String.valueOf(TokenStatus.TOKEN_IS_USED_OR_EXPIRED));
            return response;
        }

        // 결제 정보 설정
        pe.setPaymentStatus(PaymentStatus.PAYMENT_IN_PROGRESS);
        pe.setToken(optionalTokenEntity.get().getToken());
        pe.setCardRefId(optionalCardEntity.get().getCardRefId());
        pe.setTokenExpiry(optionalTokenEntity.get().getExpirationDate());
        pe.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED);
        pe.setTokenEntity(optionalTokenEntity.get());
//        pe.setCardEntity(optionalCardEntity.get());

        // 결제 정보 저장 및 ApiResponse 반환
        PayEntity savePayRequest = payRepository.save(pe);
        response.setState(String.valueOf(ResponseStatus.SUCCESS));
        response.setData(savePayRequest);
        return response;
    }

    public List<PayEntity> searchList(PayDto payDto) {

        Specification<PayEntity> spec = (root, query, criteriaBuilder) -> {
            Predicate predicate = null;
            log.info("searchList-----{}", payDto);

            /**
             * statu 만 검색
             */
            if (!StringUtils.isBlank(payDto.getPaymentStatus())) {
                predicate = PaySpecification.equalStatus(payDto.getPaymentStatus())
                        .toPredicate(root, query, criteriaBuilder);
            }

            /**
             *  status + 날짜 범위 검색
             */
            if (payDto.getStartDate() != null && payDto.getEndDate() != null) {
                predicate = PaySpecification.equalStatusAndTokenExpiryBetween(
                                payDto.getPaymentStatus(), payDto.getStartDate(), payDto.getEndDate())
                        .toPredicate(root, query, criteriaBuilder);
            }

            /**
             * 기타 다중 검색 조건을 추가 할수 있음.
             */
            return predicate;
        };
        log.info("spec========{}", spec);
        return payRepository.findAll(spec);
    }

}
