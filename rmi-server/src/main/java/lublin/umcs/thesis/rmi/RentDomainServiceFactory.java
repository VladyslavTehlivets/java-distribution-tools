package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentGameDomainService;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentGameJpaRepository;

public class RentDomainServiceFactory {
	public static RentDomainService createServiceInstance() {
		return new RentGameDomainService(new RentGameJpaRepository(), RentPriceDomainServiceFactory.createServiceInstance());
	}
}
