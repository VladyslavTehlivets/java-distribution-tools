package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;
import lublin.umcs.thesis.rmi.apiimplementation.ReturnBoardGameServiceImpl;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.gamerent.GameRentJpaRepository;

public class ReturnBoardGameServiceFactory {
	public static ReturnBoardGameService createServiceInstance() {
		return new ReturnBoardGameServiceImpl(new GameRentJpaRepository(), ReturnRentPriceDomainServiceFactory.createServiceInstance());
	}
}
