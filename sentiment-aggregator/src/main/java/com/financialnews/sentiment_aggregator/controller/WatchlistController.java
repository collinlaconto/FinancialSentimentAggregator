package com.financialnews.sentiment_aggregator.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialnews.sentiment_aggregator.dto.WatchlistResponse;
import com.financialnews.sentiment_aggregator.model.User;
import com.financialnews.sentiment_aggregator.model.Watchlist;
import com.financialnews.sentiment_aggregator.service.WatchlistService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @GetMapping
    public List<WatchlistResponse> getUserWatchlist(Authentication authentication) {
        Object userObject = authentication.getPrincipal();
        User user = User.class.cast(userObject);
        List<Watchlist> watchlistList = watchlistService.getUserWatchlist(user.getId());
    
        // Convert using streams
        return watchlistList.stream()
            .map(w -> new WatchlistResponse(w.getId(), w.getTicker(), w.getDateAdded()))
            .collect(Collectors.toList());
    }

    
    

    @PostMapping
    public WatchlistResponse addStock(Authentication authentication, @RequestBody String ticker) {
        Object userObject = authentication.getPrincipal();
        User user = User.class.cast(userObject);
        Watchlist watchlist = watchlistService.addStock(user.getId(), ticker);

        WatchlistResponse response = new WatchlistResponse(
            watchlist.getId(),
            watchlist.getTicker(), 
            watchlist.getDateAdded()
        );
        return response;
    }

    @DeleteMapping("/{id}")
    public void removeStock(@PathVariable Long id, Authentication authentication) {
        Object userObject = authentication.getPrincipal();
        User user = User.class.cast(userObject);
        watchlistService.removeStock(id, user.getId());    
    }
}
