package com.epam.esm.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class LinkModel<T> extends RepresentationModel<LinkModel<T>> {
    private final T content;

    @JsonCreator
    public LinkModel(@JsonProperty("content") T content){
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
