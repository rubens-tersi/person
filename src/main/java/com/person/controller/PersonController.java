package com.person.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.person.api.PersonApi;
import com.person.model.payload.CreatePersonPayload;
import com.person.model.payload.PersonSearchParams;
import com.person.model.payload.UpdatePersonPayload;
import com.person.model.response.PageOfPeopleResponse;
import com.person.model.response.PersonResponse;
import com.person.service.PersonService;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/people")
public class PersonController implements PersonApi {

    private final PersonService service;

    public PersonController(final PersonService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    @ResponseStatus(code = CREATED)
    public PersonResponse create(@RequestBody CreatePersonPayload payload) {
        return service.create(payload);
    }

    @Override
    @PutMapping("/{personId}")
    @ResponseStatus(code = ACCEPTED)
    public PersonResponse update(@PathVariable String personId, @RequestBody UpdatePersonPayload payload) {
        return service.update(personId, payload);
    }

    @Override
    @DeleteMapping("/{personId}")
    @ResponseStatus(code = ACCEPTED)
    public void delete(@PathVariable String personId) {
        service.delete(personId);
    }

    @Override
    @GetMapping("/{personId}")
    @ResponseStatus(code = OK)
    public PersonResponse findOne(@PathVariable String personId) {
        return service.findById(personId);
    }


    @Override
    @GetMapping
    @ResponseStatus(code = OK)
    public PageOfPeopleResponse findAll(PersonSearchParams params, Pageable page) {
        return service.findAll(params, page);
    }

}
