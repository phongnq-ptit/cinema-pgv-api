package org.cinema.queries;

import static org.cinema.jooq.tables.Users.USERS;

import jakarta.enterprise.context.ApplicationScoped;
import org.cinema.models.dto.UserDto;
import org.cinema.models.records.UserRecord.UserR;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class UserQueries {

  @Autowired
  private final DSLContext dsl;

  public UserQueries(DSLContext dsl) {
    this.dsl = dsl;
  }

  public UserDto findByEmailAndPassword(String email, String password) {
    UserR row = dsl.selectFrom(USERS)
        .where(USERS.EMAIL.eq(email)).and(USERS.PASSWORD.eq(password))
        .fetchOptional()
        .map(record -> record.into(UserR.class))
        .orElse(null);

    return UserDto.toDto(row);
  }
}
