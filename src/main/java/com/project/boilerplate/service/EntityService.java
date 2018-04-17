package com.project.boilerplate.service;

import com.project.boilerplate.dto.entity.EntityRequest;
import com.project.boilerplate.entity.Entity;
import com.project.boilerplate.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntityService implements IEntityService {

    private final EntityRepository entityRepository;

    @Autowired
    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public Entity create(EntityRequest request) {
        Entity entity = new Entity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setCreated(LocalDateTime.now());
        return entityRepository.save(entity);
    }

    @Override
    public Entity update(Entity entity, EntityRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setCreated(LocalDateTime.now());
        return entityRepository.save(entity);
    }
}
