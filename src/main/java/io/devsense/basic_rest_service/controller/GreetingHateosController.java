package io.devsense.basic_rest_service.controller;

import io.devsense.basic_rest_service.domain.model.GreetingH;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class GreetingHateosController {
    private static final String template = "Hello Hateos, %s !";

    @RequestMapping(value = "/greetingh", method = RequestMethod.GET)
    public HttpEntity<GreetingH> greetingHHttpEntity(@RequestParam(value = "name", defaultValue = "World") String name){

        GreetingH greetingH = new GreetingH(String.format(template, name));
//        greetingH.add(linkTo(methodOn(GreetingHateosController.class).greetingHHttpEntity(name)).withSelfRel());
        greetingH.add(linkTo(methodOn(GreetingController.class).greetingOldrefWithJavaconfig(name)).withRel("greetingOldrefWithJavaconfig"));
        return new ResponseEntity<>(greetingH, HttpStatus.OK);
    }
}
