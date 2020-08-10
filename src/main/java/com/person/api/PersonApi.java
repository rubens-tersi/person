package com.person.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;

import com.person.config.swagger.ApiPageable;
import com.person.model.payload.CreatePersonPayload;
import com.person.model.payload.PersonSearchParams;
import com.person.model.payload.UpdatePersonPayload;
import com.person.model.response.ErrorResponse;
import com.person.model.response.PageOfPeopleResponse;
import com.person.model.response.PersonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Person")
public interface PersonApi {

    @ApiOperation(value = "Create new Person", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 201, message = "Person created with success", response = PersonResponse.class),
            @ApiResponse(code = 400, message = "Wrong payload parameters", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Person already exists with given CPF/Passport, or Payload has duplicate Phone Numbers", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = void.class) })
    PersonResponse create(@Valid CreatePersonPayload payload);

    @ApiOperation(value = "Update existent Person", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 202, message = "Person changed with success", response = PersonResponse.class),
            @ApiResponse(code = 204, message = "No Person associated with given Id", response = void.class),
            @ApiResponse(code = 400, message = "Wrong payload parameters", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Payload has duplicate Phone Numbers (Main phone or any in list)", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = void.class) })
    PersonResponse update(@ApiParam(value = "Person associated Id.", example = "123") String personId, @Valid UpdatePersonPayload payload);

    @ApiOperation(value = "Delete Person by Id", produces = APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 202, message = "Person deleted with success", response = void.class),
            @ApiResponse(code = 204, message = "No Person associated with given Id", response = void.class),
            @ApiResponse(code = 400, message = "Wrong id format", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = void.class) })
    void delete(@ApiParam(value = "Person associated Id.", example = "123") String personId);

    @ApiOperation(value = "Find a Person by Id", produces = APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Person found", response = PersonResponse.class),
            @ApiResponse(code = 204, message = "No Person associated with given Id", response = void.class),
            @ApiResponse(code = 400, message = "Wrong id format", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = void.class) })
    PersonResponse findOne(@ApiParam(value = "Person associated Id.", example = "123") String personId);


    @ApiOperation(value = "Find all People", produces = APPLICATION_JSON_VALUE)
    @ApiResponses({ @ApiResponse(code = 200, message = "Person found (1..n)", response = PageOfPeopleResponse.class),
            @ApiResponse(code = 204, message = "No Person was found", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Wrong parameter format", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = void.class) })
    @ApiPageable
    PageOfPeopleResponse findAll(PersonSearchParams params, @ApiIgnore Pageable page);

}
