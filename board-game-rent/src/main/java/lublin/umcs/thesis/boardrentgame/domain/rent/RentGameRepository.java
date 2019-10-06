package lublin.umcs.thesis.boardrentgame.domain.rent;

import lublin.umcs.thesis.boardrentgame.domain.user.UserId;

// TODO: 10/6/19 repository
public interface RentGameRepository {
  boolean hasNoneUnfinishedRents(UserId userId);

  void save(GameRent gameRent);

  GameRent loadById(GameRent gameRent);
}
