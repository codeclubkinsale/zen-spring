package com.coderdojo.zen.belt.controllers;

import com.coderdojo.zen.belt.dto.BeltRequest;
import com.coderdojo.zen.belt.dto.BeltResponse;
import com.coderdojo.zen.belt.services.BeltService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Returns an Image object that can then be painted on the screen.
 * The url argument must specify an absolute. The name
 * argument is a specifier that is relative to the url argument.
 * <p>
 * This method always returns immediately, whether the
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives
 * that draw the image will incrementally paint on the screen.
 *
 */
@RestController
@RequestMapping("/api/belts")
public class BeltController {

    private final BeltService beltService;

    BeltController(BeltService beltService) {
        this.beltService = beltService;
    }
    /**
     * Hero is the main entity we'll be using to . . .
     * Please see the class for true identity
     * @author Captain America
     * @return EntityModel
     */
    @GetMapping()
    public List<BeltResponse> getAllBelts() {

        return beltService.getAllBelts();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBelt(@RequestBody BeltRequest beltRequest) {
        beltService.createBelt(beltRequest);
    }
}