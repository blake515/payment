package org.demo.paymentdemo.repository;

import jakarta.persistence.Id;
import java.util.Optional;
import org.demo.paymentdemo.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardRefId(String cardRefId);
    Optional<CardEntity> findByCardNumber(String cardNumber);
}
