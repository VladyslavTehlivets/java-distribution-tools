package lublin.umcs.thesis.boardrentgame.domain.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

public interface RentDomainService {

  boolean isPossibleToRealizeRentByUser(GameRent gameRent, User user);

  Price rent(GameRent gameRent, final PriceCurrency priceCurrency);
}
