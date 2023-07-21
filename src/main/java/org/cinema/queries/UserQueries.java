package org.cinema.queries;

import static org.cinema.jooq.tables.Users.USERS;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import org.cinema.models.dto.UserDto;
import org.cinema.models.records.UserRecord.UserR;
import org.cinema.models.request.RegisterRequest;
import org.cinema.utils.CommonUtiils;
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

  public UserDto findByUuid(UUID uuid) {
    UserR row = dsl.selectFrom(USERS)
        .where(USERS.UUID.eq(CommonUtiils.uuidToBytesArray(uuid)))
        .fetchOptional()
        .map(record -> record.into(UserR.class))
        .orElse(null);

    return UserDto.toDto(row);
  }

  public UserDto findByEmail(String email) {
    UserR row = dsl.selectFrom(USERS)
        .where(USERS.EMAIL.eq(email))
        .fetchOptional()
        .map(record -> record.into(UserR.class))
        .orElse(null);

    return UserDto.toDto(row);
  }

  public boolean insert(RegisterRequest newUser) {
    int result = dsl.insertInto(USERS,
        USERS.UUID,
        USERS.EMAIL,
        USERS.PASSWORD,
        USERS.ADDRESS,
        USERS.USER_NAME,
        USERS.CINEMA_ID,
        USERS.ROLE,
        USERS.ACTIVE)
        .values(
            CommonUtiils.uuidToBytesArray(newUser.getUuid()),
            newUser.getEmail(),
            CommonUtiils.hashPassword(newUser.getPassword()),
            newUser.getAddress(),
            newUser.getUserName(),
            CommonUtiils.uuidToBytesArray(newUser.getCinemaId()),
            newUser.getRole().getRole(),
            newUser.getActive()
        ).execute();

    return result != 0;
  }

  public boolean update(UUID userUuid, UserDto userUpdate) {
    int result = dsl.update(USERS)
        .set(USERS.ADDRESS, userUpdate.getAddress())
        .set(USERS.USER_NAME, userUpdate.getUserName())
        .set(USERS.CINEMA_ID, userUpdate.getCinemaId().toString().getBytes())
        .set(USERS.ACTIVE, userUpdate.getActive())
        .where(USERS.UUID.eq(CommonUtiils.uuidToBytesArray(userUuid)))
        .execute();

    return result != 0;
  }
}
