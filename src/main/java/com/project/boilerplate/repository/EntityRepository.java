package com.project.boilerplate.repository;

import com.project.boilerplate.entity.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityRepository extends MongoRepository<Entity, String> {
}
