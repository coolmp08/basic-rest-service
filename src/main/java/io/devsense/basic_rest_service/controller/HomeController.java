package io.devsense.basic_rest_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class HomeController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Map<String, Object> greeting(){

        return Collections.singletonMap("message","Hello, World");
    }
}
