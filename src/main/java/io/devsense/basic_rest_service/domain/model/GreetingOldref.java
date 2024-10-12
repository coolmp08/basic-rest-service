package io.devsense.basic_rest_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class GreetingOldref extends RepresentationModel<GreetingOldref> {

    private final String content;

    @JsonCreator
    public GreetingOldref(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
