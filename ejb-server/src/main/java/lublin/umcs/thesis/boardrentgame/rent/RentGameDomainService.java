package lublin.umcs.thesis.boardrentgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

// TODO: 10/6/19 Service
@Stateless
public class RentGameDomainService implements RentDomainService {

  @EJB
  private RentGameRepository rentGameRepository;
  @EJB
  private RentPriceService rentPriceService;

  @Override
  public boolean isPossibleToRealizeRentByUser(final GameRent gameRent, final User user) {
    return rentGameRepository.hasNoneUnfinishedRents(user);
  }

  @Override
  public Price rent(final GameRent gameRent, final PriceCurrency priceCurrency) {
    gameRent.rentGame();
    rentGameRepository.save(gameRent);
    return rentPriceService.countPrice(gameRent, priceCurrency);
  }
}
