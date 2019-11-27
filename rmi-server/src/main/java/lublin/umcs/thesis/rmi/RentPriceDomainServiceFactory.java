package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.boardrentgame.domain.rent.RentPriceDomainService;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentPriceService;
import lublin.umcs.thesis.rmi.boardrentgame.user.UserJpaRepository;

public class RentPriceDomainServiceFactory {
	public static RentPriceDomainService createServiceInstance() {
		return new RentPriceService(new UserJpaRepository());
	}
}
