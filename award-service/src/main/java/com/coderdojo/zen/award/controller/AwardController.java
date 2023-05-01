package com.coderdojo.zen.award.controller;

import com.coderdojo.zen.award.dto.AwardRequest;
import com.coderdojo.zen.award.dto.AwardResponse;
import com.coderdojo.zen.award.service.AwardService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api/awards")
public class AwardController {

    private final AwardService awardService;

    /**
     * Hero is the main entity we'll be using to . . .
     * Please see the class for true identity
     * @author Captain America
     * @return EntityModel
     */
    @GetMapping
    public List<AwardResponse> getAllAwards() {
        return awardService.getAllAwards();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAward(@RequestBody AwardRequest awardRequest) {
        awardService.createAward(awardRequest);
    }

}
