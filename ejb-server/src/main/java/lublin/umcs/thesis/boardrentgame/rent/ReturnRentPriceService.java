package lublin.umcs.thesis.boardrentgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DefaultRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.RebateFor10Rent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.PriceCalculateException;
import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ReturnRentPriceService implements ReturnRentPriceDomainService {

  @EJB
  private DomainUserRepository domainUserRepository;

  @Override public Price countPrice(final GameRent gameRent, final PriceCurrency priceCurrency, Long days) {

    final GameRebatePolicy gameRebatePolicy =
            domainUserRepository.isUserWithRebate(gameRent.getUser())
            ? new RebateFor10Rent()
            : new DefaultRebatePolicy();

    return gameRent.getBoardGames().stream()
        .peek(boardGame -> boardGame.setGameRebatePolicy(gameRebatePolicy))
        .map(boardGame -> calculateEndRentPrice(days, priceCurrency, boardGame))
        .reduce(Price::add)
        .orElseThrow(PriceCalculateException::new);
  }

  private Price calculateEndRentPrice(
      final Long dayCount, final PriceCurrency priceCurrency, final BoardGame boardGame) {
    return boardGame.getDayPrice(priceCurrency).multiply(dayCount).minus(boardGame.getGamePrice());
  }
}
