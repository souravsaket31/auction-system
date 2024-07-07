package com.lowes.app.auction.system.controller;


import com.lowes.app.auction.system.dto.Auction;
import com.lowes.app.auction.system.dto.Bid;
import com.lowes.app.auction.system.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;


    @PreAuthorize("hasRole('AUCTIONEER')")
    @PostMapping
    public Auction createAuction(@RequestBody Auction auction) {
        return auctionService.createAuction(auction);
    }

    @PreAuthorize("hasAnyRole('AUCTIONEER', 'PARTICIPANT')")
    @GetMapping("/{id}")
    public Auction getAuction(@PathVariable Long id) {
        return auctionService.getAuction(id);
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping("/{id}/bids")
    public String submitBid(@PathVariable Long id, @RequestBody Bid bid) {
        return auctionService.submitBid(id, bid);
    }

    @PreAuthorize("hasRole('AUCTIONEER')")
    @GetMapping("/{id}/status")
    public String getAuctionStatus(@PathVariable Long id) {
        return auctionService.getAuctionStatus(id);
    }

    @PreAuthorize("hasRole('AUCTIONEER')")
    @GetMapping("/{id}/highestBid")
    public BigDecimal getHighestBid(@PathVariable Long id) {
        return auctionService.getHighestBid(id);
    }
}
