package lublin.umcs.thesis.rmi.boardrentgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentState;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.gamerent.GameRentPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static lublin.umcs.thesis.rmi.RmiServer.ENTITY_MANAGER;

public class RentGameJpaRepository implements RentGameRepository {

	@Override public boolean hasNoneUnfinishedRents(final User user) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		boolean result = ENTITY_MANAGER
				.createQuery(
						"from GameRentPersistence "
								+ "where user.id = :user_id "
								+ "and rentState not in (:unfinished_states)")
				.setParameter("user_id", user.getUserId().getValue())
				.setParameter("unfinished_states", RentState.unfinishedStates())
				.getResultList()
				.size() == 0;

		entityTransaction.commit();

		return result;
	}

	@Override public void save(final GameRent gameRent) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		ENTITY_MANAGER.merge(new GameRentPersistence(gameRent));

		entityTransaction.commit();
	}

	@Override public GameRent loadById(final GameRent gameRent) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		GameRent foundGameRent = ENTITY_MANAGER.find(GameRentPersistence.class, gameRent.getGameRentId().getValue())
				.toGameRent();

		entityTransaction.commit();

		return foundGameRent;
	}

	@Override public GameRent findByGameIdAndUserId(final GameId gameId, final UserId userId) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		GameRent gameRent = ENTITY_MANAGER.createQuery(
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

		entityTransaction.commit();

		return gameRent;
	}
}
