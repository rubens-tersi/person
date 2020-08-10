package com.autopass.person.exception;

import com.autopass.person.model.enums.SocialNetwork;

import lombok.Getter;

@Getter
public class SocialNetworkAlreadyBindedException extends RuntimeException {

    private static final long serialVersionUID = -7337442978979004224L;
    private final SocialNetwork socialNetwork;

    public SocialNetworkAlreadyBindedException(SocialNetwork socialNetwork) {
        super();
        this.socialNetwork = socialNetwork;
    }

}
