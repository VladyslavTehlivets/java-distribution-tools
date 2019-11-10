package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateful
@Local
public class BoardGameJpaRepository implements BoardGameRepository {

  @PersistenceContext(unitName = "boardgame", type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  @Override
  public List<BoardGame> findGamesByNames(final String[] gameNames) {
    return entityManager.createQuery("from BoardGamePersistence where name in (:gameNames)", BoardGamePersistence.class)
            .setParameter("gameNames", gameNames)
            .getResultStream()
            .map(BoardGamePersistence::toBoardGame)
            .collect(Collectors.toList());
  }

  @Override
  public List<BoardGame> findGamesByIds(final Set<GameId> gameIds) {
    return entityManager.createQuery("from BoardGamePersistence where id in (:gameIds)", BoardGamePersistence.class)
            .setParameter("gameIds", gameIds)
            .getResultStream()
            .map(BoardGamePersistence::toBoardGame)
            .collect(Collectors.toList());
  }
}
