package com.person.model.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PageOfPeopleResponse extends PageImpl<PersonResponse> {

    private static final long serialVersionUID = -4096748679534400563L;

    public PageOfPeopleResponse(Page<PersonResponse> content) {
        super(content.getContent(), content.getPageable(), content.getTotalElements());
    }

}
