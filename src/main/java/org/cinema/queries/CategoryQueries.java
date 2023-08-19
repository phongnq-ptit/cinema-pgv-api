package org.cinema.queries;

import static org.cinema.jooq.tables.Categories.CATEGORIES;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.CategoryDto;
import org.cinema.models.records.CategoryRecord.CategoryR;
import org.cinema.utils.CommonUtils;
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

  public void insert(CategoryDto category) {
    dsl.insertInto(CATEGORIES, CATEGORIES.UUID, CATEGORIES.NAME, CATEGORIES.DESCRIPTION)
        .values(
            CommonUtils.uuidToBytesArray(UUID.randomUUID()),
            category.getName(),
            category.getDescription())
        .execute();
  }

  public void update(UUID uuid, CategoryDto categoryDto) {
    dsl.update(CATEGORIES)
        .set(CATEGORIES.NAME, categoryDto.getName())
        .set(CATEGORIES.DESCRIPTION, categoryDto.getDescription())
        .where(CATEGORIES.UUID.eq(CommonUtils.uuidToBytesArray(uuid)))
        .execute();
  }
}
