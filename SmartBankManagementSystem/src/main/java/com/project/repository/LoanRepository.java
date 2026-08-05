package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByCustomerEmail(String email);

    List<Loan> findByStatus(String status);

}
