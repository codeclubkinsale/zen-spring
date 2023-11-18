package com.coderdojo.zen.belt.controllers;

import com.coderdojo.zen.belt.dto.BeltRequest;
import com.coderdojo.zen.belt.dto.BeltResponse;
import com.coderdojo.zen.belt.services.BeltService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Javadoc
 */
@RestController
@RequestMapping("/api/belts")
public class BeltController {

    private final BeltService beltService;

    /**
     * Javadoc
     */
    BeltController(BeltService beltService) {
        this.beltService = beltService;
    }

    /**
     * Javadoc
     */
    @GetMapping()
    public List<BeltResponse> getAllBelts() {

        return beltService.getAllBelts();
    }

    /**
     * Javadoc
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBelt(@RequestBody BeltRequest beltRequest) {
        beltService.createBelt(beltRequest);
    }

    /**
     * Javadoc
     */
    @GetMapping()
    @RequestMapping("/{id}")
    public BeltResponse getBeltById(@PathVariable Long id) {

        return beltService.getBeltById(id);
    }
}