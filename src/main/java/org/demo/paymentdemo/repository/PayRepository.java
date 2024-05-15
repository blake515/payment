package org.demo.paymentdemo.repository;

import org.demo.paymentdemo.dto.PayDto;
import org.demo.paymentdemo.entity.PayEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, Long>,
        JpaSpecificationExecutor<PayEntity> {

//    @SuppressWarnings("NullableProblems")
//    Page<PayEntity> findAll(Specification<PayEntity> specification, Pageable pageable);
}
