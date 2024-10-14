package io.devsense.basic_rest_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BasicService {

    @Autowired
    ApplicationContext applicationContext;

    public void listBeans(){
        for (String beanname: applicationContext.getBeanDefinitionNames()){
            System.out.println(beanname);
        }
    }
}
