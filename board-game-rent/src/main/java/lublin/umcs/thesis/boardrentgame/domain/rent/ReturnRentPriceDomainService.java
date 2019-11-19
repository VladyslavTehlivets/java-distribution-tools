package lublin.umcs.thesis.boardrentgame.domain.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;

public interface ReturnRentPriceDomainService {
	Price countPrice(GameRent gameRent, PriceCurrency priceCurrency, Long days);
}
