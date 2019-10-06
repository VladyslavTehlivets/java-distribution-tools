package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import java.math.BigDecimal;

public class DefaultRebatePolicy implements GameRebatePolicy {

  @Override
  public Price getDayPrice(final BigDecimal gamePrice, final PriceCurrency currency) {
    final BigDecimal percent = new BigDecimal(0.25);
    return new Price(gamePrice.multiply(percent), currency);
  }
}
