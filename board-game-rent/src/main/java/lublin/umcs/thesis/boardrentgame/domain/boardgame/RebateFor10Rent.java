package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import java.math.BigDecimal;

public class RebateFor10Rent implements GameRebatePolicy {

  @Override
  public Price getDayPrice(final BigDecimal gamePrice, final PriceCurrency currency) {
    final BigDecimal percent = new BigDecimal(0.1);
    return new Price(gamePrice.multiply(percent), currency);
  }
}
