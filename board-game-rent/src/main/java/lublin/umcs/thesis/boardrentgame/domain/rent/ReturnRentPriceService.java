package lublin.umcs.thesis.boardrentgame.domain.rent;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DefaultRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.RebateFor10Rent;
import lublin.umcs.thesis.boardrentgame.domain.user.UserRepository;

// TODO: 10/6/19 Servivce
@RequiredArgsConstructor
public class ReturnRentPriceService {

  private final UserRepository userRepository;

  public Price countPrice(final GameRent gameRent, final PriceCurrency priceCurrency, Long days) {

    final GameRebatePolicy gameRebatePolicy =
        userRepository.isUserWithRebate(gameRent.getUser())
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
