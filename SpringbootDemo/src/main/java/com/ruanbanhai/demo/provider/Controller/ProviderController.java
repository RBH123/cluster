package com.ruanbanhai.demo.provider.Controller;

import com.ruanbanhai.demo.provider.Service.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    private Provider provider;

    @RequestMapping(value = "/provider", method = RequestMethod.GET)
    public void proViderTest() {
        provider.producer();
    }
}
