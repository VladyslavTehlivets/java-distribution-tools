package lublin.umcs.thesis.rmi;

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
import lublin.umcs.thesis.rmi.api.RentBoardGameService;
import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.user.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

class BoardGameRentBusinessProcessTest {

	private static Registry registry;

	@BeforeAll
	public static void setUp() throws RemoteException {
		registry = LocateRegistry.createRegistry(1100);
	}

	@Test
	void findGamesByIds() throws NamingException, RemoteException, NotBoundException {
		RentBoardGameService rentBoardGameService = RentBoardGameServiceFactory.createServiceInstance();
		RentBoardGameService rentBoardGameServiceStub = (RentBoardGameService) UnicastRemoteObject.exportObject(rentBoardGameService, 0);

		ReturnBoardGameService returnBoardGameService = ReturnBoardGameServiceFactory.createServiceInstance();
		ReturnBoardGameService returnBoardGameServiceStub = (ReturnBoardGameService) UnicastRemoteObject.exportObject(returnBoardGameService, 0);

		registry.rebind("RentBoardGameService", rentBoardGameServiceStub);
		registry.rebind("ReturnBoardGameService", returnBoardGameServiceStub);

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

		RentBoardGameService rentBoardGameServiceRMI = (RentBoardGameService) registry.lookup("RentBoardGameService");
		BigDecimal rentPrice = rentBoardGameServiceRMI.rent("11", 1L, "PLN", gameNames);

		Price gamePrice = game.getGamePrice();
		Price dayPrice = new DefaultRebatePolicy().getDayPrice(gamePrice.getValueAs(PriceCurrency.PLN), PriceCurrency.PLN);
		Assertions.assertEquals(rentPrice, dayPrice.add(gamePrice).getValueAs(PriceCurrency.PLN));

		ReturnBoardGameService returnBoardGameServiceRMI = (ReturnBoardGameService) registry.lookup("ReturnBoardGameService");
		GameRent gameRent = new RentGameJpaRepository().findByGameIdAndUserId(game.getGameId(), user.getUserId());
		BigDecimal price = returnBoardGameServiceRMI.returnBoardGameService(gameRent.getGameRentId().getValue(), PriceCurrency.PLN.name());
		Assertions.assertNotNull(price);
	}
}