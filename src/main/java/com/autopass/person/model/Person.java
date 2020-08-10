package com.autopass.person.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "people")
public class Person {

    @Id
    private String id;

    private String documentNumber;

    private String fullName;

    private String socialName;

    private String gender;

    private LocalDate birthDate;

    private String email;

    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Builder.Default
    private boolean deleted = false;

}
