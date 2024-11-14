package com.spring.library.modelData;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.library.model.Language;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorData> author,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") int downloads,
        @JsonAlias("copyright") boolean copyright
) {
}
