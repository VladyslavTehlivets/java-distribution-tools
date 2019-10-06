package lublin.umcs.thesis.boardrentgame.user;

import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserRepository;

public class UserJpaRepository implements UserRepository {
  @Override
  public void save(final User user) {}

  @Override
  public boolean isUserWithRebate(final User user) {
    return false;
  }
}
