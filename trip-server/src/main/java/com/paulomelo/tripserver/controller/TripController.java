package com.paulomelo.tripserver.controller;

import com.paulomelo.tripserver.service.DestinyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripController {

    private final DestinyService destinyService;

    public TripController(DestinyService destinyService) {
        this.destinyService = destinyService;
    }

    @GetMapping("/trip")
    public ResponseEntity<List<String>> destinies() {
        return ResponseEntity.ok(destinyService.getAllDestinies());
    }

    @GetMapping("/trip/{destiny}")
    public String carForDestiny(@PathVariable("destiny") String destiny) {
        return destinyService.getDestiny(destiny);
    }
}
