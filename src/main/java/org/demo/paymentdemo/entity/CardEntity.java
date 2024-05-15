package org.demo.paymentdemo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Table(name = "card")
@Entity
@Data
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", length = 100, unique = true)
    private String cardNumber;

    @Column(name = "card_ref_id", length = 19, unique = true)
    private String cardRefId;

    @Column(name = "owner", length = 32)
    private String owner;

    @Column(name = "expiration_date", columnDefinition = "DATE")
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "cardRefId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TokenEntity> tokens;

}
