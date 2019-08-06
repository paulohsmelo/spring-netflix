package com.paulomelo.tripserver.repository;

import com.paulomelo.tripserver.domain.Destiny;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DestinyRepository extends MongoRepository<Destiny, Long> {

    List<Destiny> findByLocation(String location);
}
