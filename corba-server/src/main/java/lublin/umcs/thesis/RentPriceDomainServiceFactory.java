package lublin.umcs.thesis;

import lublin.umcs.thesis.boardrentgame.domain.rent.RentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.rent.RentPriceService;
import lublin.umcs.thesis.boardrentgame.user.UserJpaRepository;

public class RentPriceDomainServiceFactory {
	public static RentPriceDomainService createServiceInstance() {
		return new RentPriceService(new UserJpaRepository());
	}
}
