package com.springcloud.consumers.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@FeignClient(name = "provider")
public interface ConsumerService {

    @GetMapping(path = "/provider")
    public String consumers();
}
