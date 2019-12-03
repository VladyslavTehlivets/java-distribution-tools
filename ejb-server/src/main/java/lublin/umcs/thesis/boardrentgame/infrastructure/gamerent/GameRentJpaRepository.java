package lublin.umcs.thesis.boardrentgame.infrastructure.gamerent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
@Local
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GameRentJpaRepository implements GameRentRepository {

  @PersistenceContext(unitName = "boardgame", type = PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;

  @Override
  public GameRent loadGameRentId(final GameRentId gameRentId) {
    return entityManager.find(GameRentPersistence.class, gameRentId.getValue()).toGameRent();
  }

  @Override
  public void save(final GameRent gameRent) {
    entityManager.merge(new GameRentPersistence(gameRent));
  }
}
