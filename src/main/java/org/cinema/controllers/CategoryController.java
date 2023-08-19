package org.cinema.controllers;

import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.cinema.models.dto.CategoryDto;
import org.cinema.services.data.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping()
  public Response createCategory(@RequestBody CategoryDto categoryDto) {
    return categoryService.createCategory(categoryDto);
  }

  @PatchMapping("/{uuid}")
  public Response updateCategory(
      @PathVariable(name = "uuid") UUID uuid, @RequestBody CategoryDto categoryDto) {
    return categoryService.updateCategory(uuid, categoryDto);
  }
}
