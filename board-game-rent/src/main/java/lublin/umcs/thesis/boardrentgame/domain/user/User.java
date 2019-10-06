package lublin.umcs.thesis.boardrentgame.domain.user;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class User {

  private final UserId userId;
  private final UserName userName;

  public User(final UserId userId, final UserName userName) {
    this.userId = requireNonNull(userId, "Id Can't be null");
    this.userName = requireNonNull(userName, "Name Can't be null");
  }
}
