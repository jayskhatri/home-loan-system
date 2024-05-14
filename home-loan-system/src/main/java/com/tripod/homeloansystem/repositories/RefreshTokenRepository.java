package com.tripod.homeloansystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.tripod.homeloansystem.models.Person;
import com.tripod.homeloansystem.models.RefreshToken;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(Person user);

    @Modifying
    int deleteByUser(Person user);
}
