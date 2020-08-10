package com.autopass.person.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.autopass.person.model.Person;
import com.autopass.person.model.enums.DocumentType;

public interface PersonRepository extends MongoRepository<Person, String>, QueryByExampleExecutor<Person> {

    boolean existsByDocumentNumberAndDeletedFalse(String documentNumber);

    Optional<Person> findByPersonIdAndDeletedFalse(Long personId);

    Optional<Person> findByDocumentTypeAndDocumentNumberAndDeletedFalse(DocumentType documentType, String documentNumber);

}
