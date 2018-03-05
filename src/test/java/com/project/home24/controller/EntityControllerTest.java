package com.project.home24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.home24.dto.entity.EntityRequest;
import com.project.home24.entity.Entity;
import com.project.home24.exception.EntityNotFoundException;
import com.project.home24.repository.AccountRepository;
import com.project.home24.repository.EntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@MockBean({
        AccountRepository.class
})
@WebMvcTest(EntityController.class)
public class EntityControllerTest {

    private final static String DESCRIPTION = "Test description";
    private final static String TITLE = "Test title";
    private final static String ID = "1absd9";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EntityRepository entityRepository;

    @Test
    public void create() throws Exception {

        when(entityRepository.insert((Entity) any())).thenReturn(getTestEntity());

        mockMvc.perform(MockMvcRequestBuilders.post("/entities")
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(getTestEntityRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("description").isNotEmpty())
                .andExpect(jsonPath("title").isNotEmpty())
                .andExpect(jsonPath("created").isNotEmpty());
    }

    @Test
    public void getOneSuccessful() throws Exception {

        when(entityRepository.findOne(anyString())).thenReturn(getTestEntity());

        mockMvc.perform(get("/entities/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("description").isNotEmpty())
                .andExpect(jsonPath("title").isNotEmpty())
                .andExpect(jsonPath("description").isNotEmpty());

    }

    @Test
    public void getOneNotFound() throws Exception {

        when(entityRepository.findOne(anyString())).thenThrow(new EntityNotFoundException(any()));

        mockMvc.perform(get("/entities/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").isNotEmpty());

        verify(entityRepository, times(1)).findOne(anyString());
    }

    @Test
    public void getAllSuccessful() throws Exception {

        when(entityRepository.findAll()).thenReturn(Collections.singletonList(getTestEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get("/entities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").isNotEmpty())
                .andExpect(jsonPath("$[0].description").isNotEmpty())
                .andExpect(jsonPath("$[0].created").isNotEmpty());
    }

    @Test
    public void updateOneSuccessful() throws Exception {

        when(entityRepository.findOne(anyString())).thenReturn(getTestEntity());
        when(entityRepository.save(getTestEntity())).thenReturn(getTestEntity());

        mockMvc.perform(MockMvcRequestBuilders.put("/entities/{id}", ID)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(getTestEntityRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("description").isNotEmpty())
                .andExpect(jsonPath("title").isNotEmpty())
                .andExpect(jsonPath("created").isNotEmpty())
                .andExpect(jsonPath("updated").isNotEmpty());
    }

    @Test
    public void updateOneNotFound() throws Exception {
        when(entityRepository.findOne(anyString())).thenThrow(new EntityNotFoundException(any()));

        mockMvc.perform(get("/entities/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").isNotEmpty());

        verify(entityRepository, times(1)).findOne(anyString());
    }

    @Test
    public void deleteOneSuccessful() throws Exception {
        when(entityRepository.findOne(anyString())).thenReturn(getTestEntity());

        mockMvc.perform(delete("/entities/{id}", ID))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOneNotFound() throws Exception {
        when(entityRepository.findOne(anyString())).thenThrow(new EntityNotFoundException(any()));

        mockMvc.perform(get("/entities/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").isNotEmpty());

        verify(entityRepository, times(1)).findOne(anyString());
    }

    private Entity getTestEntity() {
        Entity entity = new Entity();
        entity.setTitle(TITLE);
        entity.setDescription(DESCRIPTION);
        entity.setCreated(LocalDateTime.now());
        return entity;
    }

    private EntityRequest getTestEntityRequest() {
        EntityRequest entityRequest = new EntityRequest();
        entityRequest.setDescription(DESCRIPTION);
        entityRequest.setTitle(TITLE);
        return entityRequest;
    }

    private String toJson(EntityRequest entityRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(entityRequest);
    }
}
