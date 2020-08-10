package com.autopass.person.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.autopass.person.exception.PersonAssociationException;
import com.autopass.person.exception.PersonNotFoundException;
import com.autopass.person.model.Person;
import com.autopass.person.model.payload.CreatePersonPayload;
import com.autopass.person.model.payload.PersonSearchParams;
import com.autopass.person.model.payload.UpdatePersonPayload;
import com.autopass.person.model.response.PageOfPeopleResponse;
import com.autopass.person.model.response.PersonResponse;
import com.autopass.person.repository.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public PersonResponse create(CreatePersonPayload payload) {
        verify(repository.existsByDocumentNumberAndDeletedFalse(payload.getDocumentNumber()));

        return new PersonResponse(repository.save(createPerson(payload)));
    }

    public PersonResponse update(Long personId, UpdatePersonPayload payload) {
        return repository.findByPersonIdAndDeletedFalse(personId).map(person -> updatePerson(person, payload)).map(PersonResponse::new)
                .orElseThrow(PersonNotFoundException::new);
    }

    public void delete(Long personId) {
        final var foundPerson = repository.findByPersonIdAndDeletedFalse(personId).orElseThrow(PersonNotFoundException::new);

        foundPerson.setDeleted(true);

        repository.save(foundPerson);
    }

    public PersonResponse findById(Long personId) {
        var person = repository.findByPersonIdAndDeletedFalse(personId).orElseThrow(PersonNotFoundException::new);

        return new PersonResponse(person);
    }


    public PageOfPeopleResponse findAll(PersonSearchParams params, Pageable page) {
        final var result = repository.findAll(example(params), page);

        if (result.isEmpty())
            throw new PersonNotFoundException();

        return new PageOfPeopleResponse(result.map(PersonResponse::new));
    }


    private Person createPerson(CreatePersonPayload payload) {
        return Person.builder().documentNumber(payload.getDocumentNumber())
                .fullName(payload.getFullName().trim().toUpperCase())
                .socialName(Optional.ofNullable(payload.getSocialName()).map(socialName -> socialName.trim().toUpperCase()).orElse(null))
                .gender(payload.getGender()).birthDate(payload.getBirthDate())
                .email(payload.getEmail()).build();
    }

    private Person updatePerson(Person person, UpdatePersonPayload payload) {
        person.setFullName(payload.getFullName().trim().toUpperCase());
        person.setSocialName(Optional.ofNullable(payload.getSocialName()).map(socialName -> socialName.trim().toUpperCase()).orElse(null));
        person.setEmail(payload.getEmail());
        person.setGender(payload.getGender());
        person.setBirthDate(payload.getBirthDate());

        return repository.save(person);
    }

    private void verify(boolean exists) {
        if (exists) {
            throw new PersonAssociationException();
        }
    }

    private Person filters(PersonSearchParams params) {
        return Person.builder().fullName(params.getFullName()).socialName(params.getSocialName()).gender(params.getGender()).build();
    }

    private Example<Person> example(PersonSearchParams params) {
        return Example.of(filters(params), params.getMode().matcher().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase());
    }


}
