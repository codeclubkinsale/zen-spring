package com.coderdojo.zen.badge.repository;

import com.coderdojo.zen.badge.model.Badge;
import com.coderdojo.zen.badge.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByCategory(Category category);
}