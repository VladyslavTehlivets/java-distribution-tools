package lublin.umcs.thesis.boardrentgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentState;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentPersistence;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
@Local
public class RentGameJpaRepository implements RentGameRepository {

	@PersistenceContext(unitName = "boardgame", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Override public boolean hasNoneUnfinishedRents(final User user) {
		return entityManager
				.createQuery(
						"from GameRentPersistence "
								+ "where user.id = :user_id "
								+ "and rentState not in (:unfinished_states)")
				.setParameter("user_id", user.getUserId().getValue())
				.setParameter("unfinished_states", RentState.unfinishedStates())
				.getResultList()
				.size() > 0;
	}

	@Override public void save(final GameRent gameRent) {
		entityManager.persist(new GameRentPersistence(gameRent));
	}

	@Override public GameRent loadById(final GameRent gameRent) {
		return entityManager.find(GameRentPersistence.class, gameRent.getGameRentId().getValue())
				.toGameRent();
	}
}
