package org.demo.paymentdemo.service;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.demo.paymentdemo.entity.PayEntity;
import org.springframework.data.jpa.domain.Specification;

@Log4j2
public class PaySpecification {
    public static Specification<PayEntity> equalStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            log.info("paymentStatus======={}", status);
            return criteriaBuilder.equal(root.get("paymentStatus"), status);
        };
    }
    public static Specification<PayEntity> equalStatusAndTokenExpiryBetween(String status, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            log.info("status , startDate , endDate ======={},{},{}", status, startDate, endDate);
            Predicate statusPredicate = criteriaBuilder.equal(root.get("paymentStatus"), status);

            LocalDateTime startDateTime = startDate.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endDateTime = endDate.withHour(23).withMinute(59).withSecond(59);

            Predicate datePredicate = criteriaBuilder.between(root.get("tokenExpiry"), startDateTime, endDateTime);

            return criteriaBuilder.and(statusPredicate, datePredicate);
        };
    }
}
