package io.devsense.basic_rest_service.controller;

import io.devsense.basic_rest_service.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    public BasicService basicService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Map<String, Object> greeting(){
//        basicService.listBeans();
        return Collections.singletonMap("message","Hello, World");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public Map<String, Object> home(){
        return Collections.singletonMap("message","Welcome to Home!");
    }
}
