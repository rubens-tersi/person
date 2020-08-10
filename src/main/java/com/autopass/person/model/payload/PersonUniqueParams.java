package com.autopass.person.model.payload;

import com.autopass.person.util.validator.ValidDocument;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonUniqueParams {

    @ApiModelProperty("Search by person's document")
    @ValidDocument(message = "{invalid.document}")
    private String document;

}
