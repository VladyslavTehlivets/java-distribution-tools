package lublin.umcs.thesis.rmi.boardrentgame.infrastructure.gamerent;

import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;

public class GameRentJpaRepository implements GameRentRepository {

	@PersistenceUnit(unitName = "boardgame")
	private EntityManagerFactory entityManagerFactory;

	@Override
	public GameRent loadGameRentId(final GameRentId gameRentId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		GameRent gameRent = entityManager.find(GameRentPersistence.class, gameRentId.getValue()).toGameRent();

		entityTransaction.commit();

		return gameRent;
	}

	@Override
	public void save(final GameRent gameRent) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.merge(new GameRentPersistence(gameRent));

		entityTransaction.commit();
	}
}
