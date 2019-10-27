package lublin.umcs.thesis.boardrentgame.infrastructure.gamerent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateless
public class GameRentJpaRepository implements GameRentRepository {

  @PersistenceContext(unitName = "lublin.umcs.thesis", type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  @Override
  public GameRent loadGameRentId(final GameRentId gameRentId) {
    return entityManager.find(GameRentPersistence.class, gameRentId.getValue()).toGameRent();
  }

  @Override
  public void save(final GameRent gameRent) {}
}
