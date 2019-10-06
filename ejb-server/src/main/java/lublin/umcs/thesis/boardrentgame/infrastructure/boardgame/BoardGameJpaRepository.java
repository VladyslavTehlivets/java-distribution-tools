package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless
public class BoardGameJpaRepository implements BoardGameRepository {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public List<BoardGame> findGamesByNames(final String[] gameNames) {
    return null;
  }

  @Override
  public List<BoardGame> findGamesByIds(final Set<GameId> gameIds) {
    return null;
  }
}
