package lublin.umcs.thesis.boardrentgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentState;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentPersistence;

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
public class RentGameJpaRepository implements RentGameRepository {

	@PersistenceContext(unitName = "boardgame", type = PersistenceContextType.TRANSACTION)
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
				.size() == 0;
	}

	@Override public void save(final GameRent gameRent) {
		entityManager.merge(new GameRentPersistence(gameRent));
	}

	@Override public GameRent loadById(final GameRent gameRent) {
		return entityManager.find(GameRentPersistence.class, gameRent.getGameRentId().getValue())
				.toGameRent();
	}

	@Override public GameRent findByGameIdAndUserId(final GameId gameId, final UserId userId) {
		return entityManager.createQuery(
				"select grp "
						+ "from GameRentPersistence grp "
						+ "join grp.games gp "
						+ "where grp.user.id = :user_id "
						+ "and gp.gameId = :game_id  ",
				GameRentPersistence.class)
				.setParameter("user_id", userId.getValue())
				.setParameter("game_id", gameId.getValue())
				.getSingleResult()
				.toGameRent();
	}
}
