package lublin.umcs.thesis.boardrentgame.domain.user;

public interface UserRepository {
  void save(User user);

  boolean isUserWithRebate(final User user); // todo user with rents % 10 == 0
}
