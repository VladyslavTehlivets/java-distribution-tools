package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.rmi.boardrentgame.rent.ReturnRentPriceService;
import lublin.umcs.thesis.rmi.boardrentgame.user.UserJpaRepository;

public class ReturnRentPriceDomainServiceFactory {
	public static ReturnRentPriceDomainService createServiceInstance() {
		return new ReturnRentPriceService(new UserJpaRepository());
	}
}
