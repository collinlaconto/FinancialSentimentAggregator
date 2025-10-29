package com.financialnews.sentiment_aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financialnews.sentiment_aggregator.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long>{
    List<Watchlist> findByUser_Id(Long userId);
    boolean existsByUser_IdAndTicker(Long userId, String ticker);
}
