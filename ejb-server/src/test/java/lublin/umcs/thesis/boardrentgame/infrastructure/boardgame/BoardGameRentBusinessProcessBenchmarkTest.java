package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DomainBoardGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameDescription;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameName;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.domain.user.UserName;
import lublin.umcs.thesis.ejb.boardgame.rent.RentBoardGameServiceRemote;
import lublin.umcs.thesis.ejb.boardgame.returngame.ReturnBoardGameServiceRemote;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

@State(Scope.Benchmark)
public class BoardGameRentBusinessProcessBenchmarkTest {

	private static InitialContext initialContext;

	@Setup(Level.Invocation)
	public static void setUp() throws NamingException {
		Properties properties = new Properties();

		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.openejb.client.LocalInitialContextFactory");
		properties.put("log4j.category.OpenEJB.options ", " debug");
		properties.put("log4j.category.OpenEJB.startup ", " debug");
		properties.put("log4j.category.OpenEJB.startup.config ", " debug");
		properties.put("boardGame", "new://Resource?type=DataSource");
		properties.put("boardGame.JdbcDriver", "org.h2.Driver");
		properties.put("boardGame.JdbcUrl", "jdbc:h2:mem:boardGame");
		properties.put("boardGame.JtaManaged", "true");
		properties.put("boardgame.hibernate.show_sql", "true");
		properties.put("boardgame.hibernate.format_sql", "true");
		properties.put("boardgame.hibernate.hbm2ddl.auto", "update");

		properties.put("openejb.validation.output.level", "verbose");
		initialContext = new InitialContext(properties);
	}

	@Benchmark
	@Fork(value = 2, warmups = 2)
	@BenchmarkMode(Mode.AverageTime)
	public void shouldPassBusinessProcess() throws NamingException {
		User user = new User(new UserId(UUID.randomUUID().toString()), new UserName(RandomStringUtils.random(12)));

		BoardGame game = BoardGame.builder()
				.gameId(new GameId(UUID.randomUUID().toString()))
				.gamePrice(new Price(12, PriceCurrency.PLN))
				.name(new GameName(RandomStringUtils.random(13)))
				.gameDescription(new GameDescription("Game about life"))
				.build();

		DomainUserRepository domainUserRepositoryLocal = (DomainUserRepository) initialContext.lookup("UserJpaRepositoryLocal");
		domainUserRepositoryLocal.save(user);
		DomainBoardGameRepository boardGameJpaRepository = (DomainBoardGameRepository) initialContext.lookup("BoardGameJpaRepositoryLocal");
		boardGameJpaRepository.save(game);
		ArrayList<String> gameNames = new ArrayList<>();
		gameNames.add(game.getName().getValue());

		RentBoardGameServiceRemote rentBoardGameServiceRemote = (RentBoardGameServiceRemote) initialContext.lookup("RentBoardGameServiceRemote");
		rentBoardGameServiceRemote.rent(user.getUserId().getValue(), 1L, "PLN", gameNames);

		ReturnBoardGameServiceRemote returnBoardGameServiceRemote = (ReturnBoardGameServiceRemote) initialContext.lookup("ReturnBoardGameServiceRemote");
		RentGameRepository rentGameRepository = (RentGameRepository) initialContext.lookup("RentGameJpaRepositoryLocal");
		GameRent gameRent = rentGameRepository.findByGameIdAndUserId(game.getGameId(), user.getUserId());
		returnBoardGameServiceRemote.returnBoardGameService(gameRent.getGameRentId().getValue(), PriceCurrency.PLN.name());
	}

	public static class BenchmarkRunner {
		public static void main(String[] args) throws Exception {
			org.openjdk.jmh.Main.main(args);
		}
	}
}