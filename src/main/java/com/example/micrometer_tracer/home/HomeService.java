package com.example.micrometer_tracer.home;

import org.springframework.web.service.annotation.GetExchange;

public interface HomeService {

    @GetExchange("/home")
    String home();
}
