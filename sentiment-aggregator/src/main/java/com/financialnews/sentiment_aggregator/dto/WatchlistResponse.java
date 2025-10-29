package com.financialnews.sentiment_aggregator.dto;

import java.time.LocalDateTime;

public class WatchlistResponse {
    private Long id;
    private String ticker;
    private LocalDateTime dateAdded;

    public WatchlistResponse(Long id, String ticker, LocalDateTime dateAdded) {
        this.id = id;
        this.ticker = ticker;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }
}