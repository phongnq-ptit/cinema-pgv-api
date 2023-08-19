package org.cinema.services.data;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.CategoryDto;
import org.cinema.models.response.BaseResponse;
import org.cinema.queries.CategoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  @Autowired private final CategoryQueries categoryQueries;

  public CategoryService(CategoryQueries categoryQueries) {
    this.categoryQueries = categoryQueries;
  }

  public Response getListCategories() {
    return Response.ok()
        .entity(
            new BaseResponse<List<CategoryDto>>(
                200, "lay ra danh sach the loai thanh cong", categoryQueries.getListCategories()))
        .build();
  }

  public Response createCategory(CategoryDto dto) {
    categoryQueries.insert(dto);

    return Response.noContent().build();
  }

  public Response updateCategory(UUID uuid, CategoryDto dto) {
    categoryQueries.update(uuid, dto);

    return Response.noContent().build();
  }
}
