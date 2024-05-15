package org.demo.paymentdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.demo.paymentdemo.common.status.PaymentStatus;

@Entity
@Table(name = "payments")
@Data
public class PayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "token_expiry", nullable = false)
    private LocalDateTime tokenExpiry;

    @Column(name = "card_ref_id", nullable = false)
    private String cardRefId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    //    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "token_entity_id", referencedColumnName = "id")
//    private TokenEntity tokenEntity;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "card_entity_id", referencedColumnName = "id")
//    private CardEntity cardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
//    @JoinColumn(name = "crid", referencedColumnName = "card_ref_id")
    @JoinColumn(name = "card_ref_id", referencedColumnName = "card_ref_id", insertable = false, updatable = false)
    private CardEntity cardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
//    @JoinColumn(name = "token_id", referencedColumnName = "id")
    @JoinColumn(name = "token_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TokenEntity tokenEntity;

}
