package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency.PLN;

public class Price {

  private final BigDecimal value;

  @Getter private final PriceCurrency currency;

  public Price(final Integer value, final PriceCurrency currency) {
    this.currency = requireNonNull(currency, "Currency can't be null");
    final Integer price = requireNonNull(value, "Price can't be null");
    this.value = currency.toPLN(new BigDecimal(price));
  }

  public Price(final BigDecimal value, final PriceCurrency currency) {
    this.currency = requireNonNull(currency, "Currency can't be null");
    final BigDecimal price = requireNonNull(value, "Price can't be null");
    this.value = currency.toPLN(price);
  }

  public Price(final Double value, final PriceCurrency currency) {
    this.currency = requireNonNull(currency, "Currency can't be null");
    final Double price = requireNonNull(value, "Price can't be null");
    this.value = currency.toPLN(new BigDecimal(price));
  }

  public BigDecimal getValueAs(PriceCurrency currency) {
    return currency.fromPLN(value);
  }

  public Price add(final Price other) {
    return new Price(this.getValueAs(PLN).add(other.getValueAs(PLN)), PLN);
  }

  public Price minus(final Price other) {
    return new Price(this.getValueAs(PLN).subtract(other.getValueAs(PLN)), PLN);
  }

  public Price multiply(final Long value) {
    return new Price(this.getValueAs(PLN).multiply(new BigDecimal(value)), PLN);
  }

  public Price negate(final PriceCurrency priceCurrency) {
    final BigDecimal negate = this.getValueAs(priceCurrency).negate();
    return new Price(negate, priceCurrency);
  }
}
