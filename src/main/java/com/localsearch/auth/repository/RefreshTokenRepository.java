package com.localsearch.auth.repository;

import com.localsearch.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
