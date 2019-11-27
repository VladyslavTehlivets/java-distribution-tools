package lublin.umcs.thesis;

import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.rent.RentGameDomainService;
import lublin.umcs.thesis.boardrentgame.rent.RentGameJpaRepository;

public class RentDomainServiceFactory {
	public static RentDomainService createServiceInstance() {
		return new RentGameDomainService(new RentGameJpaRepository(), RentPriceDomainServiceFactory.createServiceInstance());
	}
}
