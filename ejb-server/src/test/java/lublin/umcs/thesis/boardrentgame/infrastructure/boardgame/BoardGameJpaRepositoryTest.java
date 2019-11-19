package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DefaultRebatePolicy;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

class BoardGameJpaRepositoryTest {

	private static InitialContext initialContext;

	@BeforeAll
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

	@Test
	void findGamesByIds() throws NamingException {
		User user = new User(new UserId("11"), new UserName("Tehlivets"));

		BoardGame game = BoardGame.builder()
				.gameId(new GameId("1"))
				.gamePrice(new Price(12, PriceCurrency.PLN))
				.name(new GameName("Beautiful game"))
				.gameDescription(new GameDescription("Game about life"))
				.build();

		DomainUserRepository domainUserRepositoryLocal = (DomainUserRepository) initialContext.lookup("UserJpaRepositoryLocal");
		domainUserRepositoryLocal.save(user);
		DomainBoardGameRepository boardGameJpaRepository = (DomainBoardGameRepository) initialContext.lookup("BoardGameJpaRepositoryLocal");
		boardGameJpaRepository.save(game);
		ArrayList<String> gameNames = new ArrayList<>();
		gameNames.add(game.getName().getValue());

		RentBoardGameServiceRemote rentBoardGameServiceRemote = (RentBoardGameServiceRemote) initialContext.lookup("RentBoardGameServiceRemote");
		BigDecimal rentPrice = rentBoardGameServiceRemote.rent("11", 1L, "PLN", gameNames);

		Price gamePrice = game.getGamePrice();
		Price dayPrice = new DefaultRebatePolicy().getDayPrice(gamePrice.getValueAs(PriceCurrency.PLN), PriceCurrency.PLN);
		Assertions.assertEquals(rentPrice, dayPrice.add(gamePrice).getValueAs(PriceCurrency.PLN));

		ReturnBoardGameServiceRemote returnBoardGameServiceRemote = (ReturnBoardGameServiceRemote) initialContext.lookup("ReturnBoardGameServiceRemote");
		RentGameRepository rentGameRepository = (RentGameRepository) initialContext.lookup("RentGameJpaRepositoryLocal");
		GameRent gameRent = rentGameRepository.findByGameIdAndUserId(game.getGameId(), user.getUserId());
		BigDecimal price = returnBoardGameServiceRemote.returnBoardGameService(gameRent.getGameRentId().getValue(), PriceCurrency.PLN.name());
		Assertions.assertNotNull(price);
	}
}