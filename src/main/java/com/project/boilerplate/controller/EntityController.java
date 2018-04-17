package com.project.boilerplate.controller;

import com.project.boilerplate.dto.entity.EntityRequest;
import com.project.boilerplate.entity.Entity;
import com.project.boilerplate.exception.EntityNotFoundException;
import com.project.boilerplate.repository.EntityRepository;
import com.project.boilerplate.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/entities")
public class EntityController {

    private final EntityRepository entityRepository;
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityRepository entityRepository, EntityService entityService) {
        this.entityRepository = entityRepository;
        this.entityService = entityService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Entity create(@Valid @RequestBody EntityRequest entityRequest) {
        return entityService.create(entityRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Entity getOne(@PathVariable("id") String id) throws EntityNotFoundException {
        return Optional.ofNullable(entityRepository.findOne(id)).orElseThrow(() -> new EntityNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List getAll() {
        return entityRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Entity updateOne(@Valid @RequestBody EntityRequest entityRequest, @PathVariable("id") String id)
            throws EntityNotFoundException {
        Entity entity = Optional.ofNullable(entityRepository.findOne(id)).orElseThrow(() -> new EntityNotFoundException(id));
        entityRepository.save(entityService.update(entity, entityRequest));
        return entity;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") String id) {
        Entity entity = Optional.ofNullable(entityRepository.findOne(id))
                .orElseThrow(() -> new EntityNotFoundException(id));
        entityRepository.delete(entity.getId());
    }
}
