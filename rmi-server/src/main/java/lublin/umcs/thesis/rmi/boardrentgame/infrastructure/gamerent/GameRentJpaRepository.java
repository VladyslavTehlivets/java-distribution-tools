package lublin.umcs.thesis.rmi.boardrentgame.infrastructure.gamerent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static lublin.umcs.thesis.rmi.RmiServer.ENTITY_MANAGER;

public class GameRentJpaRepository implements GameRentRepository {

	@Override
	public GameRent loadGameRentId(final GameRentId gameRentId) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();
		GameRent gameRent = ENTITY_MANAGER.find(GameRentPersistence.class, gameRentId.getValue()).toGameRent();

		entityTransaction.commit();

		return gameRent;
	}

	@Override
	public void save(final GameRent gameRent) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		ENTITY_MANAGER.merge(new GameRentPersistence(gameRent));

		entityTransaction.commit();
	}
}
