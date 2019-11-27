package lublin.umcs.thesis;

import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.rent.ReturnRentPriceService;
import lublin.umcs.thesis.boardrentgame.user.UserJpaRepository;

public class ReturnRentPriceDomainServiceFactory {
	public static ReturnRentPriceDomainService createServiceInstance() {
		return new ReturnRentPriceService(new UserJpaRepository());
	}
}
