package org.cinema.controllers;

import jakarta.ws.rs.core.Response;
import org.cinema.services.data.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  @Autowired private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping()
  public Response getListCategories() {
    return categoryService.getListCategories();
  }
}
