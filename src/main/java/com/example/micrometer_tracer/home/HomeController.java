package com.example.micrometer_tracer.home;

import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    Tracer tracer;

    @GetMapping("/home")
    public String home() {
        tracer.createBaggageInScope("baggage", "bagger");
        return homeService.home();
    }
}
