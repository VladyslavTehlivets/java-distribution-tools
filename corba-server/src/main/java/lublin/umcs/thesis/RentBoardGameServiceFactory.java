package lublin.umcs.thesis;

import lublin.umcs.thesis.api.RentBoardGameService;
import lublin.umcs.thesis.apiimplementation.RentBoardGameServiceImpl;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.boardrentgame.user.UserJpaRepository;

import java.rmi.RemoteException;

import static java.util.Objects.isNull;

public class RentBoardGameServiceFactory {
	private static RentBoardGameService INSTANCE;

	private static void createServiceInstance() throws RemoteException {
		INSTANCE = new RentBoardGameServiceImpl(new BoardGameJpaRepository(), RentDomainServiceFactory.createServiceInstance(), new UserJpaRepository());
	}

	public static RentBoardGameService getSingletonInstance() throws RemoteException {
		if (isNull(INSTANCE)) {
			createServiceInstance();
		}
		return INSTANCE;
	}
}
