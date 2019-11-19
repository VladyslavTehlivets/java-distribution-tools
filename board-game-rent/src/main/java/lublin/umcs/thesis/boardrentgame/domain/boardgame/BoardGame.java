package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class BoardGame {
  @Getter private final GameId gameId;
  @Getter private final Price gamePrice;
  @Getter private final GameName name;
  @Getter private final GameDescription gameDescription;
  @Setter private GameRebatePolicy gameRebatePolicy;
  @Getter private GameRent gameRent;

  @Builder
  public BoardGame(
          final GameId gameId,
          final Price gamePrice,
          final GameName name,
          final GameDescription gameDescription, final GameRent gameRent) {
    this.gameId = requireNonNull(gameId);
    this.gamePrice = requireNonNull(gamePrice);
    this.name = requireNonNull(name);
    this.gameDescription = requireNonNull(gameDescription);
    this.gameRent = gameRent;
  }

  public Price getDayPrice(PriceCurrency currency) {
    return gameRebatePolicy.getDayPrice(gamePrice.getValueAs(currency), currency);
  }

  public Price getRentPrice(PriceCurrency currency) {
    final BigDecimal percent = new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP);
    return new Price(gamePrice.getValueAs(currency).multiply(percent), currency);
  }

  public void blockByRent(final GameRent gameRent) {
    checkArgument(isNull(this.gameRent), "Can't rent already rented game");
    this.gameRent = requireNonNull(gameRent);
  }

  public void unBlock() {
    checkArgument(nonNull(gameRent), "Game is not rented");
    this.gameRent = null;
  }
}
