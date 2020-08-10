package com.person.model.payload;

import org.springframework.data.domain.ExampleMatcher;

public enum SearchMode {

    MATCHING_ALL {
        @Override
        public ExampleMatcher matcher() {
            return ExampleMatcher.matchingAll();
        }
    },
    MATCHING_ANY {
        @Override
        public ExampleMatcher matcher() {
            return ExampleMatcher.matchingAny();
        }
    };

    public abstract ExampleMatcher matcher();
}
