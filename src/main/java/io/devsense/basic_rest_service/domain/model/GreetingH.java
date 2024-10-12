package io.devsense.basic_rest_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class GreetingH extends RepresentationModel<GreetingH> {

    private final String content;

    @JsonCreator
    public GreetingH(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}