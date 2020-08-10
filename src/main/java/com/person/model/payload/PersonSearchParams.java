package com.autopass.person.model.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonSearchParams {

    @ApiModelProperty("Search filtering mode. May be MATCHING ALL params, or MATCHING_ANY param. By default, MATCHING_ALL is configured.")
    private SearchMode mode = SearchMode.MATCHING_ALL;

    @ApiModelProperty("Search by person's full name")
    private String fullName;

    @ApiModelProperty("Search by person's social name")
    private String socialName;

    @ApiModelProperty("Search by person's gender")
    private String gender;

}
