package com.coderdojo.zen.belt.services;

import com.coderdojo.zen.belt.dto.BeltRequest;
import com.coderdojo.zen.belt.dto.BeltResponse;
import com.coderdojo.zen.belt.model.Belt;
import com.coderdojo.zen.belt.repositories.BeltRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Javadoc
 */
@Service
public class BeltService {
    private final BeltRepository beltRepository;

    /**
     * Javadoc
     */
    BeltService(BeltRepository beltRepository) {
        this.beltRepository = beltRepository;
    }

    public void createBelt(BeltRequest beltRequest) {
        Belt belt = Belt.builder()
                .name(beltRequest.name())
                .description(beltRequest.description())
                .imageURL(beltRequest.imageURL())
                .build();

        beltRepository.save(belt);
    }

    /**
     * Javadoc
     */
    public List<BeltResponse> getAllBelts() {
        List<Belt> belts = beltRepository.findAll();

        return belts.stream().map(this::mapToBeltResponse).toList();
    }

    /**
     * Javadoc
     */
    public BeltResponse getBeltById(Long id) {
        Optional<Belt> optionalBelt = beltRepository.findById(id);
        if (optionalBelt.isPresent()) {
            Belt belt = optionalBelt.get();
            return BeltResponse.builder()
                    .name(belt.getName())
                    .description(belt.getDescription())
                    .imageURL(belt.getImageURL())
                    .build();
        } else {
            return null;
        }
    }

    private BeltResponse mapToBeltResponse(Belt belt) {
        return new BeltResponse(
                belt.getId(),
                belt.getName(),
                belt.getDescription(),
                belt.getImageURL()
        );
    }
}
