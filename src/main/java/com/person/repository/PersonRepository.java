package com.person.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.person.model.Person;

public interface PersonRepository extends MongoRepository<Person, String>, QueryByExampleExecutor<Person> {

    boolean existsByDocumentNumberAndDeletedFalse(String documentNumber);

    Optional<Person> findByIdAndDeletedFalse(String personId);

}
