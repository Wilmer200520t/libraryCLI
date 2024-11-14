package com.spring.library.modelData;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record ResponseData(
        @JsonAlias("count") Long count,
        @JsonAlias("next") String next_url,
        @JsonAlias("previous") String previous_url,
        @JsonAlias("results") List<BookData> books
        ) {
}
