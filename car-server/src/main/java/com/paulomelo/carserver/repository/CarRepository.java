package com.paulomelo.carserver.repository;

import com.paulomelo.carserver.domain.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRepository extends MongoRepository<Car, Long> {

    List<Car> findByModel(String model);
}
