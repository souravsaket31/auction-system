package com.lowes.app.auction.system.repository;

import com.lowes.app.auction.system.dto.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByAuctioneerId(Long auctioneerId);
}