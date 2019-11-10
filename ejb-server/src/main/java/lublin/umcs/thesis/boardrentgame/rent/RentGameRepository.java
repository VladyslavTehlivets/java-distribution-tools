package lublin.umcs.thesis.boardrentgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

public interface RentGameRepository {
  boolean hasNoneUnfinishedRents(User user);

  void save(GameRent gameRent);

  GameRent loadById(GameRent gameRent);
}
