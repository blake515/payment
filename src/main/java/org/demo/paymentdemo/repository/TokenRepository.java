package org.demo.paymentdemo.repository;

import java.util.Optional;
import org.demo.paymentdemo.entity.CardEntity;
import org.demo.paymentdemo.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(String token);
}
