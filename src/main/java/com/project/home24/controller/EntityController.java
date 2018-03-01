package com.project.home24.controller;

import com.project.home24.dto.entity.EntityRequest;
import com.project.home24.entity.Entity;
import com.project.home24.exception.EntityNotFoundException;
import com.project.home24.repository.EntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/entities")
public class EntityController {

    private static final Logger log = LoggerFactory.getLogger(EntityController.class);

    private final EntityRepository entityRepository;

    public EntityController(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Entity create(@Valid @RequestBody EntityRequest entityRequest) {
        Entity entity = new Entity();
        entity.setTitle(entityRequest.getTitle());
        entity.setDescription(entityRequest.getDescription());
        return entityRepository.insert(entity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Entity getOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        Optional<Entity> opt = Optional.ofNullable(entityRepository.findOne(id));
        return opt.orElseThrow(() -> new EntityNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Entity> getAll() {
        return entityRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Entity updateOne(@Valid @RequestBody EntityRequest entityRequest, @PathVariable("id") Long id)
            throws EntityNotFoundException {
        Optional<Entity> opt = Optional.ofNullable(entityRepository.findOne(id));
        Entity entity = opt.orElseThrow(() -> new EntityNotFoundException(id));
        entity.setDescription(entityRequest.getDescription());
        entity.setTitle(entityRequest.getTitle());
        return entityRepository.save(entity);
    }


}
