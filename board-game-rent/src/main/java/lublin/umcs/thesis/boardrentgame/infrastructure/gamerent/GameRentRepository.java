package lublin.umcs.thesis.boardrentgame.infrastructure.gamerent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;

public interface GameRentRepository {
  GameRent loadGameRentId(GameRentId gameRentId);

  void save(GameRent gameRent);
}
