package com.omrixyz.dfmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omrixyz.dfmh.model.TokenRegister;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface TokenRepository extends JpaRepository<TokenRegister, Long> {
    TokenRegister findByTokenID(String tokenID);
}
