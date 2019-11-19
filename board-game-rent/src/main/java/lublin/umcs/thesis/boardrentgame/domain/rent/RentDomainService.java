package lublin.umcs.thesis.boardrentgame.domain.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

import java.math.BigDecimal;

public interface RentDomainService {

  boolean isPossibleToRealizeRentByUser(GameRent gameRent, User user);

  BigDecimal rent(GameRent gameRent, final PriceCurrency priceCurrency);
}
