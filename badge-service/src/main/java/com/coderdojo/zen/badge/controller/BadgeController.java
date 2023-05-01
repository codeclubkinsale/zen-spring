package com.coderdojo.zen.badge.controller;

import com.coderdojo.zen.badge.dto.BadgeRequest;
import com.coderdojo.zen.badge.dto.BadgeResponse;
import com.coderdojo.zen.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    /**
     * Hero is the main entity we'll be using to . . .
     * Please see the class for true identity
     * @author Captain America
     * @return EntityModel
     */
    @GetMapping
    public List<BadgeResponse> getAllBadges() {
        return badgeService.getAllBadges();
    }

    @PostMapping
    public void createBadge(@RequestBody BadgeRequest badgeRequest) {
        badgeService.createBadge(badgeRequest);
    }

    @GetMapping()
    @RequestMapping("/{id}")
    public BadgeResponse getBadgeById(@PathVariable Long id) {

        return badgeService.getBadgeById(id);
    }


}