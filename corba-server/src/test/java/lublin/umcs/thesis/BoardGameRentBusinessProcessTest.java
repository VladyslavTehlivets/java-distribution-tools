package lublin.umcs.thesis;

import lublin.umcs.thesis.api.RentBoardGameService;
import lublin.umcs.thesis.api.ReturnBoardGameService;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DefaultRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameDescription;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameName;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.domain.user.UserName;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.boardrentgame.rent.RentGameJpaRepository;
import lublin.umcs.thesis.boardrentgame.user.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

class BoardGameRentBusinessProcessTest {

	private static Context context;

	static {
		try {
			RentBoardGameService rentBoardGameService = RentBoardGameServiceFactory.getSingletonInstance();
			ReturnBoardGameService returnBoardGameService = ReturnBoardGameServiceFactory.getSingletonInstance();

			Properties properties = new Properties();
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
			properties.put(Context.PROVIDER_URL, "iiop://localhost:1050");
			context = new InitialContext(properties);
			context.rebind("RentBoardGameService", rentBoardGameService);
			context.rebind("ReturnBoardGameService", returnBoardGameService);
		} catch (RemoteException | NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	void findGamesByIds() throws NamingException, RemoteException {
		User user = new User(new UserId("11"), new UserName("Tehlivets"));

		BoardGame game = BoardGame.builder()
				.gameId(new GameId("1"))
				.gamePrice(new Price(12, PriceCurrency.PLN))
				.name(new GameName("Beautiful game"))
				.gameDescription(new GameDescription("Game about life"))
				.build();

		new UserJpaRepository().save(user);
		new BoardGameJpaRepository().save(game);

		ArrayList<String> gameNames = new ArrayList<>();
		gameNames.add(game.getName().getValue());

		RentBoardGameService rentBoardGameServiceRemote = (RentBoardGameService) context.lookup("RentBoardGameService");
		BigDecimal rentPrice = rentBoardGameServiceRemote.rent("11", 1L, "PLN", gameNames);

		Price gamePrice = game.getGamePrice();
		Price dayPrice = new DefaultRebatePolicy().getDayPrice(gamePrice.getValueAs(PriceCurrency.PLN), PriceCurrency.PLN);
		Assertions.assertEquals(rentPrice, dayPrice.add(gamePrice).getValueAs(PriceCurrency.PLN));

		ReturnBoardGameService returnBoardGameServiceRemote = (ReturnBoardGameService) context.lookup("ReturnBoardGameService");
		GameRent gameRent = new RentGameJpaRepository().findByGameIdAndUserId(game.getGameId(), user.getUserId());
		BigDecimal price = returnBoardGameServiceRemote.returnBoardGameService(gameRent.getGameRentId().getValue(), PriceCurrency.PLN.name());
		Assertions.assertNotNull(price);
	}
}