package lublin.umcs.thesis.rmi.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DomainBoardGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardGameJpaRepository implements BoardGameRepository, DomainBoardGameRepository {

	@PersistenceUnit(unitName = "boardgame")
	private EntityManagerFactory entityManagerFactory;

	@Override
	public List<BoardGame> findGamesByNames(final List<String> gameNames) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<BoardGame> boardGames = entityManager.createQuery("from BoardGamePersistence where name in (:gameNames)", BoardGamePersistence.class)
				.setParameter("gameNames", gameNames)
				.getResultStream()
				.map(BoardGamePersistence::toBoardGame)
				.collect(Collectors.toList());

		entityTransaction.commit();

		return boardGames;
	}

	@Override
	public List<BoardGame> findGamesByIds(final Set<GameId> gameIds) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<BoardGame> boardGames = entityManager.createQuery("from BoardGamePersistence where id in (:gameIds)", BoardGamePersistence.class)
				.setParameter("gameIds", gameIds)
				.getResultStream()
				.map(BoardGamePersistence::toBoardGame)
				.collect(Collectors.toList());

		entityTransaction.commit();

		return boardGames;
	}

	@Override public void save(final BoardGame boardGame) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(new BoardGamePersistence(boardGame));

		entityTransaction.commit();
	}
}
