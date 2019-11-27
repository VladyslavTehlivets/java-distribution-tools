package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameDescription;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameName;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.domain.user.UserName;
import lublin.umcs.thesis.rmi.RentBoardGameServiceFactory;
import lublin.umcs.thesis.rmi.ReturnBoardGameServiceFactory;
import lublin.umcs.thesis.rmi.api.RentBoardGameService;
import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.user.UserJpaRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BoardGameRentBusinessProcessBenchmarkTest {

	private static Registry registry;

	@Setup(Level.Invocation)
	public static void setUp() throws RemoteException {
		RentBoardGameService rentBoardGameService = RentBoardGameServiceFactory.createServiceInstance();
		RentBoardGameService rentBoardGameServiceStub = (RentBoardGameService) UnicastRemoteObject.exportObject(rentBoardGameService, 0);

		ReturnBoardGameService returnBoardGameService = ReturnBoardGameServiceFactory.createServiceInstance();
		ReturnBoardGameService returnBoardGameServiceStub = (ReturnBoardGameService) UnicastRemoteObject.exportObject(returnBoardGameService, 0);

		registry = LocateRegistry.createRegistry(1100);
		registry.rebind("RentBoardGameService", rentBoardGameServiceStub);
		registry.rebind("ReturnBoardGameService", returnBoardGameServiceStub);
	}

	@Benchmark
	@Fork(value = 2, warmups = 2)
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.NANOSECONDS)
	public void shouldPassBusinessProcess() throws NamingException, RemoteException, NotBoundException {
		User user = new User(new UserId(UUID.randomUUID().toString()), new UserName(RandomStringUtils.random(12)));

		BoardGame game = BoardGame.builder()
				.gameId(new GameId(UUID.randomUUID().toString()))
				.gamePrice(new Price(12, PriceCurrency.PLN))
				.name(new GameName(RandomStringUtils.random(13)))
				.gameDescription(new GameDescription("Game about life"))
				.build();

		new UserJpaRepository().save(user);
		new BoardGameJpaRepository().save(game);
		ArrayList<String> gameNames = new ArrayList<>();
		gameNames.add(game.getName().getValue());

		RentBoardGameService rentBoardGameServiceRemote = (RentBoardGameService) registry.lookup("RentBoardGameService");
		rentBoardGameServiceRemote.rent(user.getUserId().getValue(), 1L, "PLN", gameNames);

		ReturnBoardGameService returnBoardGameServiceRemote = (ReturnBoardGameService) registry.lookup("ReturnBoardGameService");
		GameRent gameRent = new RentGameJpaRepository().findByGameIdAndUserId(game.getGameId(), user.getUserId());
		returnBoardGameServiceRemote.returnBoardGameService(gameRent.getGameRentId().getValue(), PriceCurrency.PLN.name());
	}

	public static class BenchmarkRunner {
		public static void main(String[] args) throws Exception {
			org.openjdk.jmh.Main.main(args);
		}
	}
}