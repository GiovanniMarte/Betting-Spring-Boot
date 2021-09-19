package com.giovanni.bettingapp.repository;

import com.giovanni.bettingapp.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {
}
