package com.coderdojo.zen.belt.repositories;

import com.coderdojo.zen.belt.model.Belt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BeltRepository extends MongoRepository<Belt, Long> {
}