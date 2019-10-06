package lublin.umcs.thesis.boardrentgame.domain.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;

public interface RentDomainService {

  boolean isPossibleToRealizeRentByUser(GameRent gameRent, UserId userId);

  Price rent(GameRent gameRent, final PriceCurrency priceCurrency);
}
