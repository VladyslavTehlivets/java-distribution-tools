package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import java.math.BigDecimal;

public interface GameRebatePolicy {

  Price getDayPrice(BigDecimal gamePrice, PriceCurrency currency);
}
