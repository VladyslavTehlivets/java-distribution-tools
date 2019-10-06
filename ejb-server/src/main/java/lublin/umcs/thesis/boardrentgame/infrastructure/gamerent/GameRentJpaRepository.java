package lublin.umcs.thesis.boardrentgame.infrastructure.gamerent;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@RequiredArgsConstructor
public class GameRentJpaRepository implements GameRentRepository {

  @PersistenceContext private final EntityManager entityManager;

  @Override
  public GameRent loadGameRentId(final GameRentId gameRentId) {
    return null;
  }

  @Override
  public void save(final GameRent gameRent) {}
}
