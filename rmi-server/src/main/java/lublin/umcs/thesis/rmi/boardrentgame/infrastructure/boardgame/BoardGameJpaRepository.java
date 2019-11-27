package lublin.umcs.thesis.rmi.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DomainBoardGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameRepository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lublin.umcs.thesis.rmi.RmiServer.ENTITY_MANAGER;

public class BoardGameJpaRepository implements BoardGameRepository, DomainBoardGameRepository {

	@Override
	public List<BoardGame> findGamesByNames(final List<String> gameNames) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		List<BoardGame> boardGames = ENTITY_MANAGER.createQuery("from BoardGamePersistence where name in (:gameNames)", BoardGamePersistence.class)
				.setParameter("gameNames", gameNames)
				.getResultStream()
				.map(BoardGamePersistence::toBoardGame)
				.collect(Collectors.toList());

		entityTransaction.commit();

		return boardGames;
	}

	@Override
	public List<BoardGame> findGamesByIds(final Set<GameId> gameIds) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		List<BoardGame> boardGames = ENTITY_MANAGER.createQuery("from BoardGamePersistence where id in (:gameIds)", BoardGamePersistence.class)
				.setParameter("gameIds", gameIds)
				.getResultStream()
				.map(BoardGamePersistence::toBoardGame)
				.collect(Collectors.toList());

		entityTransaction.commit();

		return boardGames;
	}

	@Override public void save(final BoardGame boardGame) {

		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		ENTITY_MANAGER.persist(new BoardGamePersistence(boardGame));

		entityTransaction.commit();
	}
}
