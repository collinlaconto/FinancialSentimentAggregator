package com.financialnews.sentiment_aggregator.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financialnews.sentiment_aggregator.model.User;
import com.financialnews.sentiment_aggregator.model.Watchlist;
import com.financialnews.sentiment_aggregator.repository.UserRepository;
import com.financialnews.sentiment_aggregator.repository.WatchlistRepository;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Watchlist addStock(Long userId, String ticker) {
        if (repository.existsByUser_IdAndTicker(userId, ticker)) {
            throw new RuntimeException("Stock already in watchlist");
        }
        
        Watchlist watchlist = new Watchlist();

        watchlist.setTicker(ticker);
        watchlist.setDateAdded(LocalDateTime.now());

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            watchlist.setUser(user);
        }
        else {
            throw new RuntimeException("User does not exist");
        }
        repository.save(watchlist);
        return watchlist;
    }

    public List<Watchlist> getUserWatchlist(Long userId) {
        return repository.findByUser_Id(userId);
    }

    public void removeStock(Long watchlistId, Long userId) {
        Optional<Watchlist> watchlistOptional = repository.findById(watchlistId);

        if (watchlistOptional.isPresent()) {
            Watchlist watchlist = watchlistOptional.get();
            if (watchlist.getUser().getId().equals(userId)) {
                repository.delete(watchlist);
            }
            else {
                throw new RuntimeException("Invalid user");
            }
        }
        else {
            throw new RuntimeException("Invalid watchlist");
        }
    }
}
