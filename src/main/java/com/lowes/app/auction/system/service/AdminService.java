package com.lowes.app.auction.system.service;

import com.lowes.app.auction.system.dto.Auction;
import com.lowes.app.auction.system.repository.AuctionRepository;
import com.lowes.app.auction.system.repository.BidRepository;
import com.lowes.app.auction.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowes.app.auction.system.dto.Bid;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateReport() {
        LocalDate today = LocalDate.now();
        List<Auction> auctions = auctionRepository.findAll();

        long auctionsCount = auctions.size();
        long successfulAuctions = auctions.stream().filter(a -> "SUCCESS".equals(a.getStatus())).count();
        long failedAuctions = auctions.stream().filter(a -> "FAILED".equals(a.getStatus())).count();


        Map<Long, Long> participantBids = bidRepository.findAll().stream()
                .collect(Collectors.groupingBy(Bid::getParticipantId, Collectors.counting()));

        List<Map.Entry<Long, Long>> topParticipants = participantBids.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .toList();

        Map<Long, Long> auctioneerAuctions = auctions.stream()
                .collect(Collectors.groupingBy(Auction::getAuctioneerId, Collectors.counting()));

        List<Map.Entry<Long, Long>> topAuctioneers = auctioneerAuctions.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .toList();

         return String.format("Report for %s:\nTotal Auctions: %d\nSuccessful Auctions: %d\nFailed Auctions: %d\nTop 10 Participants: %s\nTop 10 Auctioneers: %s",
                today, auctionsCount, successfulAuctions, failedAuctions, topParticipants, topAuctioneers);
    }
}
