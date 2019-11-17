package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;

import java.util.List;
import java.util.Set;

public interface BoardGameRepository {
  List<BoardGame> findGamesByNames(List<String> gameNames);

  List<BoardGame> findGamesByIds(Set<GameId> gameIds);
}
