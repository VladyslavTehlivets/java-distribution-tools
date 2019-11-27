package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.rmi.api.RentBoardGameService;
import lublin.umcs.thesis.rmi.apiimplementation.RentBoardGameServiceImpl;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.user.UserJpaRepository;

public class RentBoardGameServiceFactory {
	public static RentBoardGameService createServiceInstance() {
		return new RentBoardGameServiceImpl(new BoardGameJpaRepository(), RentDomainServiceFactory.createServiceInstance(), new UserJpaRepository());
	}
}
