package com.project.home24.repository;

import com.project.home24.entity.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityRepository extends MongoRepository<Entity, Long> {
    Entity getAllEntities();
}
