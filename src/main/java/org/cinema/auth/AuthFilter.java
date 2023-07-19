package org.cinema.auth;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.cinema.models.dto.UserDto;
import org.cinema.models.response.BaseResponse;

@Provider
public class AuthFilter implements ContainerRequestFilter {
  private static final String AUTHORIZATION = "Authorization";
  @Inject
  private JwtTokenUtils jwt;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    UriInfo info = containerRequestContext.getUriInfo();
    if (info.getPath().startsWith("/api")) {
      Optional<UserDto> user = getAuthentication(containerRequestContext.getHeaders());
      if (user.isEmpty()) {
        Response response = Response.status(Response.Status.FORBIDDEN)
            .entity(new BaseResponse<>(403, "Wrong token!", null)).build();
        containerRequestContext.abortWith(response);
        return;
      }

      containerRequestContext.setSecurityContext(new JwtSecurityContext(user.get(), true));
    }
  }

  private Optional<UserDto> getAuthentication(MultivaluedMap<String, String> headers) {
    final List<String> authorization = headers.get(AUTHORIZATION);

    if (authorization == null || authorization.isEmpty()) {
      return Optional.empty();
    }
    final String token = authorization.get(0).replaceFirst("Bearer ", "");
    try {
      UserDto user = jwt.verifyToken(token);
      if (user == null || StringUtils.isEmpty(user.getUuid().toString())) {
        return Optional.empty();
      }
      return Optional.of(user);
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }

  public static class JwtSecurityContext implements SecurityContext {
    private final UserDto user;
    private final Boolean secured;

    public JwtSecurityContext(UserDto user, boolean secured) {
      this.user = user;
      this.secured = secured;
    }

    @Override
    public Principal getUserPrincipal() {
      return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
      return Boolean.TRUE;
    }

    @Override
    public boolean isSecure() {
      return this.secured;
    }

    @Override
    public String getAuthenticationScheme() {
      return SecurityContext.FORM_AUTH;
    }
  }
}
