package com.example.micrometer_tracer;

import com.example.micrometer_tracer.home.HomeService;
import com.example.micrometer_tracer.post.JsonPlaceHolderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class MicrometerTracerApplication {

  @Autowired MeterRegistry meterRegistry;

  public static void main(String[] args) {
    SpringApplication.run(MicrometerTracerApplication.class, args);
  }

  @Bean
  CommandLineRunner counterCommandLineRunner() {
    return args -> {
      Counter counter = Counter.builder("firstCounter").register(meterRegistry);
      counter.increment();
      counter.increment();
      counter.increment();
    };
  }

  @Bean
  JsonPlaceHolderService jsonPlaceHolderService() {
    RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
    HttpServiceProxyFactory httpServiceProxyFactory =
        HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    return httpServiceProxyFactory.createClient(JsonPlaceHolderService.class);
  }

  @Bean
  CommandLineRunner commandLineRunner(JsonPlaceHolderService jsonPlaceHolderService) {
    return args -> {
      jsonPlaceHolderService.findAll();
    };
  }

  @Bean
  HomeService homeService() {
    RestClient restClient =
        RestClient.builder()
            .baseUrl("http://localhost:8081")
            .defaultHeader("baggage", "bagger")
            .build();
    HttpServiceProxyFactory factory =
        HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
    return factory.createClient(HomeService.class);
  }
}
