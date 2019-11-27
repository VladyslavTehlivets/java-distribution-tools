package lublin.umcs.thesis;

import lublin.umcs.thesis.api.ReturnBoardGameService;
import lublin.umcs.thesis.apiimplementation.ReturnBoardGameServiceImpl;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentJpaRepository;

import java.rmi.RemoteException;

import static java.util.Objects.isNull;

public class ReturnBoardGameServiceFactory {
	private static ReturnBoardGameService INSTANCE;

	private static void createServiceInstance() throws RemoteException {
		INSTANCE = new ReturnBoardGameServiceImpl(new GameRentJpaRepository(), ReturnRentPriceDomainServiceFactory.createServiceInstance());
	}

	public static ReturnBoardGameService getSingletonInstance() throws RemoteException {
		if (isNull(INSTANCE)) {
			createServiceInstance();
		}
		return INSTANCE;
	}
}
