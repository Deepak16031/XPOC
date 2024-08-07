package com.example.micrometer_tracer.post;

import io.micrometer.tracing.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final JsonPlaceHolderService jsonPlaceHolderService;
  private final Tracer tracer;

  public PostController(JsonPlaceHolderService jsonPlaceHolderService, Tracer tracer) {
    this.jsonPlaceHolderService = jsonPlaceHolderService;
    this.tracer = tracer;
  }

  @GetMapping
  List<Post> findAllPosts() {
    addBaggage();
    return jsonPlaceHolderService.findAll();
  }

  @GetMapping("/{id}")
  Post findPostById(@PathVariable Long id) {
    return jsonPlaceHolderService.findById(id);
  }

  private void addBaggage() {
    tracer.createBaggageInScope("baggage", "bagger");
  }
}
