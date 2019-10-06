package lublin.umcs.thesis.boardrentgame.rent;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.DefaultRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameRebatePolicy;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.RebateFor10Rent;
import lublin.umcs.thesis.boardrentgame.domain.rent.DayCount;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.PriceCalculateException;
import lublin.umcs.thesis.boardrentgame.domain.user.UserRepository;

@RequiredArgsConstructor
// TODO: 10/6/19 service
public class RentPriceService {

  // TODO: 10/6/19 repositories
  private final UserRepository userRepository;

  public Price countPrice(final GameRent gameRent, final PriceCurrency priceCurrency) {

    final GameRebatePolicy gameRebatePolicy =
        userRepository.isUserWithRebate(gameRent.getUser())
            ? new RebateFor10Rent()
            : new DefaultRebatePolicy();

    return gameRent.getBoardGames().stream()
        .peek(boardGame -> boardGame.setGameRebatePolicy(gameRebatePolicy))
        .map(
            boardGame ->
                calculateRentPriceForGame(gameRent.getDayCount(), priceCurrency, boardGame))
        .reduce(Price::add)
        .orElseThrow(PriceCalculateException::new);
  }

  private Price calculateRentPriceForGame(
      final DayCount dayCount, final PriceCurrency priceCurrency, final BoardGame boardGame) {
    return boardGame
        .getDayPrice(priceCurrency)
        .multiply(dayCount.getValue())
        .add(boardGame.getGamePrice());
  }
}
