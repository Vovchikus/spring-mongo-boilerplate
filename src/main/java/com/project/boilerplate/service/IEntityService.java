package com.project.boilerplate.service;

import com.project.boilerplate.dto.entity.EntityRequest;
import com.project.boilerplate.entity.Entity;

public interface IEntityService {

    Entity create(EntityRequest request);

    Entity update(Entity entity, EntityRequest request);
}
