package com.example.demotdd.repository;

import com.example.demotdd.model.TddOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<TddOrder, Long> {
}

//JPAREPOSITORY INTEREFACE IS PROVIDED BY SPRING DATA JPA-CONTAINS CRUD
//TDDORDER=ENTITY
//LONG=ID TYPE