package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public enum PriceCurrency {
  EURO {
    @Override
    public BigDecimal fromPLN(final BigDecimal value) {
      return value.divide(new BigDecimal(4.1), HALF_UP);
    }

    @Override
    public BigDecimal toPLN(final BigDecimal value) {
      return value.multiply(new BigDecimal(4.21));
    }
  },
  DOLLAR {
    @Override
    public BigDecimal fromPLN(final BigDecimal value) {
      return value.divide(new BigDecimal(3.67), HALF_UP);
    }

    @Override
    public BigDecimal toPLN(final BigDecimal value) {
      return value.multiply(new BigDecimal(3.76));
    }
  },
  PLN {
    @Override
    public BigDecimal fromPLN(final BigDecimal value) {
      return value;
    }

    @Override
    public BigDecimal toPLN(final BigDecimal value) {
      return value;
    }
  };

  public abstract BigDecimal fromPLN(final BigDecimal value);

  public abstract BigDecimal toPLN(final BigDecimal value);
}
