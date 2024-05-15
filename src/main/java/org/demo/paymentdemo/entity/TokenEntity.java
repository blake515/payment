package org.demo.paymentdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "card_ref_id") // CardEntity의 cardRefId를 여기에 저장
    private String cardRefId;

    @Column(name = "token_yn")
    private boolean tokenYn;

    @Column(name = "expiration_date", columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expirationDate;

    // CardEntity의 cardRefId를 가져와서 TokenEntity의 cardRefId에 설정하는 메서드
    public void setCardRefIdFromCardEntity(CardEntity cardEntity) {
        this.cardRefId = cardEntity.getCardRefId();
    }

}
