package com.financialnews.sentiment_aggregator.repository;

import com.financialnews.sentiment_aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    
}
