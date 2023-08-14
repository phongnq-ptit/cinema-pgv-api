package org.cinema.queries;

import static org.cinema.jooq.tables.Users.USERS;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Objects;
import java.util.UUID;
import org.cinema.models.dto.UserDto;
import org.cinema.models.records.UserRecord.UserR;
import org.cinema.models.request.RegisterRequest;
import org.cinema.utils.CommonUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class UserQueries {

  @Autowired private final DSLContext dsl;

  public UserQueries(DSLContext dsl) {
    this.dsl = dsl;
  }

  public UserDto findByEmailAndPassword(String email, String password) {
    UserR row =
        dsl.selectFrom(USERS)
            .where(USERS.EMAIL.eq(email))
            .and(USERS.PASSWORD.eq(password))
            .fetchOptional()
            .map(record -> record.into(UserR.class))
            .orElse(null);

    return UserDto.toDto(row);
  }

  public UserDto findByUuid(UUID uuid) {
    UserR row =
        dsl.selectFrom(USERS)
            .where(USERS.UUID.eq(CommonUtils.uuidToBytesArray(uuid)))
            .fetchOptional()
            .map(record -> record.into(UserR.class))
            .orElse(null);

    return UserDto.toDto(row);
  }

  public UserDto findByEmail(String email) {
    UserR row =
        dsl.selectFrom(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOptional()
            .map(record -> record.into(UserR.class))
            .orElse(null);

    return UserDto.toDto(row);
  }

  public boolean insert(RegisterRequest newUser) {
    int result =
        dsl.insertInto(
                USERS,
                USERS.UUID,
                USERS.EMAIL,
                USERS.PASSWORD,
                USERS.ADDRESS,
                USERS.USER_NAME,
                USERS.CINEMA_ID,
                USERS.ROLE,
                USERS.ACTIVE)
            .values(
                CommonUtils.uuidToBytesArray(newUser.getUuid()),
                newUser.getEmail(),
                CommonUtils.hashPassword(newUser.getPassword()),
                newUser.getAddress(),
                newUser.getUserName(),
                CommonUtils.uuidToBytesArray(newUser.getCinemaId()),
                newUser.getRole().getRole(),
                newUser.getActive())
            .execute();

    return result != 0;
  }

  public boolean update(UUID userUuid, UserDto userUpdate) {
    var query =
        dsl.update(USERS)
            .set(USERS.ADDRESS, userUpdate.getAddress())
            .set(USERS.USER_NAME, userUpdate.getUserName())
            .set(USERS.ACTIVE, userUpdate.getActive());

    if (Objects.nonNull(userUpdate.getCinemaId())) {
      query = query.set(USERS.CINEMA_ID, CommonUtils.uuidToBytesArray(userUpdate.getCinemaId()));
    }

    int result = query.where(USERS.UUID.eq(CommonUtils.uuidToBytesArray(userUuid))).execute();

    return result != 0;
  }
}
