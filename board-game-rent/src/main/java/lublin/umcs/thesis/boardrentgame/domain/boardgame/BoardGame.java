package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class BoardGame {
  @Getter private final GameId gameId;
  @Getter private final Price gamePrice;
  @Getter private final GameName name;
  @Getter private final GameDescription gameDescription;
  @Setter private GameRebatePolicy gameRebatePolicy;

  @Builder
  public BoardGame(
      final GameId gameId,
      final Price gamePrice,
      final GameName name,
      final GameDescription gameDescription) {
    this.gameId = requireNonNull(gameId);
    this.gamePrice = requireNonNull(gamePrice);
    this.name = requireNonNull(name);
    this.gameDescription = requireNonNull(gameDescription);
  }

  public Price getDayPrice(PriceCurrency currency) {
    return gameRebatePolicy.getDayPrice(gamePrice.getValueAs(currency), currency);
  }

  public Price getRentPrice(PriceCurrency currency) {
    final BigDecimal percent = new BigDecimal(1.5);
    return new Price(gamePrice.getValueAs(currency).multiply(percent), currency);
  }
}
