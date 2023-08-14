package org.cinema.queries;

import static org.cinema.jooq.tables.Categories.CATEGORIES;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.cinema.models.dto.CategoryDto;
import org.cinema.models.records.CategoryRecord.CategoryR;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class CategoryQueries {
  @Autowired private final DSLContext dsl;

  public CategoryQueries(DSLContext dsl) {
    this.dsl = dsl;
  }

  public List<CategoryDto> getListCategories() {
    List<CategoryR> categories =
        dsl.selectFrom(CATEGORIES)
            .orderBy(CATEGORIES.ID.asc())
            .fetch()
            .map(record -> record.into(CategoryR.class));

    return categories.stream().map(categoryR -> CategoryDto.toDto(categoryR)).toList();
  }
}
