package lublin.umcs.thesis.boardrentgame.rent;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;

@RequiredArgsConstructor
// TODO: 10/6/19 Service
public class RentGameDomainService implements RentDomainService {

  private final RentGameRepository rentGameRepository;
  private final RentPriceService rentPriceService;

  @Override
  public boolean isPossibleToRealizeRentByUser(final GameRent gameRent, final UserId userId) {
    return rentGameRepository.hasNoneUnfinishedRents(userId);
  }

  @Override
  public Price rent(final GameRent gameRent, final PriceCurrency priceCurrency) {
    gameRent.rentGame();
    rentGameRepository.save(gameRent);
    return rentPriceService.countPrice(gameRent, priceCurrency);
  }
}
