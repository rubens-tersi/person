package com.autopass.person.model.payload;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.autopass.person.config.LocalDateDeserializer;
import com.autopass.person.config.LocalDateSerializer;
import com.autopass.person.util.validator.CompositeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonPayload {

    @ApiModelProperty(value = "Person's complete name.", required = true)
    @NotBlank(message = "{Person.fullName.notBlank}")
    @CompositeName(message = "{Person.fullName.isNotComposite}")
    @Size(max = 120, message = "{Person.fullName.size}")
    private String fullName;

    @ApiModelProperty(value = "Person's social name.", required = false)
    @Size(max = 120, message = "{Person.socialName.size}")
    private String socialName;

    @ApiModelProperty(value = "Person's gender, without fixed values.", required = true)
    @NotBlank(message = "{Person.gender.notBlank}")
    private String gender;

    @ApiModelProperty(value = "Person's birth date, following rules about user's age.", required = true)
    @NotNull(message = "{Person.birthDate.notNull}")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @ApiModelProperty(value = "Person's associated e-mail address, following requirements.", required = true)
    @Size(max = 80, message = "{Person.email.size}")
    @Email(message = "{Person.email.isInvalid}")
    @NotBlank(message = "{Person.email.notBlank}")
    private String email;

}
