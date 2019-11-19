package lublin.umcs.thesis.boardrentgame.domain.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;

public interface RentGameRepository {
  boolean hasNoneUnfinishedRents(User user);

  void save(GameRent gameRent);

  GameRent loadById(GameRent gameRent);

  GameRent findByGameIdAndUserId(final GameId gameId, final UserId userId);
}
