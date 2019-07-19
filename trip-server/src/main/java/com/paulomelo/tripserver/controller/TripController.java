package com.paulomelo.tripserver.controller;

import com.paulomelo.tripserver.dto.ErrorResponse;
import com.paulomelo.tripserver.dto.TripResponse;
import com.paulomelo.tripserver.exception.DestinyNotFoundException;
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
    public ResponseEntity<TripResponse> destinies() {
        return ResponseEntity.ok(TripResponse.withData(destinyService.getAllDestinies()));
    }

    @GetMapping("/trip/{destiny}")
    public ResponseEntity<TripResponse> carForDestiny(@PathVariable("destiny") String destiny) {
        try {
            return ResponseEntity.ok(TripResponse.withData(destinyService.getDestiny(destiny)));
        } catch (DestinyNotFoundException ex) {
            return ResponseEntity.badRequest().body(TripResponse.withError(new ErrorResponse(ex.getMessage())));
        }
    }
}
