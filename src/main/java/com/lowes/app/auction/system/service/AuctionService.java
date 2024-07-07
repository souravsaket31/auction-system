package com.lowes.app.auction.system.service;


import com.lowes.app.auction.system.dto.Auction;
import com.lowes.app.auction.system.dto.Bid;
import com.lowes.app.auction.system.repository.AuctionRepository;
import com.lowes.app.auction.system.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    public Auction createAuction(Auction auction) {
        auction.setStatus("ONGOING");
        return auctionRepository.save(auction);
    }

    public Auction getAuction(Long id) {
        return auctionRepository.findById(id).orElse(null);
    }

    public String submitBid(Long id, Bid bid) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction == null) {
            return "Auction not found";
        }
        if (LocalDateTime.now().isBefore(auction.getStartTime()) || LocalDateTime.now().isAfter(auction.getEndTime())) {
            return "Bidding time is over";
        }
        List<Bid> bids = bidRepository.findByAuctionId(id);
        if (!bids.isEmpty() && bid.getBidAmount().compareTo(bids.get(bids.size() - 1).getBidAmount()) <= 0) {
            return "Bid amount must be higher than the current highest bid";
        }
        bid.setAuctionId(id);
        bid.setBidTime(LocalDateTime.now());
        bidRepository.save(bid);
        return "Bid submitted successfully";
    }

    public String getAuctionStatus(Long id) {
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction == null) {
            return "Auction not found";
        }
        List<Bid> bids = bidRepository.findByAuctionId(id);
        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
            if (bids.isEmpty() || bids.get(bids.size() - 1).getBidAmount().compareTo(auction.getReservedPrice()) < 0) {
                auction.setStatus("FAILED");
            } else {
                auction.setStatus("SUCCESS");
            }
            auctionRepository.save(auction);
        }
        return auction.getStatus();
    }

    public BigDecimal getHighestBid(Long id) {
        List<Bid> bids = bidRepository.findByAuctionId(id);
        if (bids.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return bids.get(bids.size() - 1).getBidAmount();
    }
}
