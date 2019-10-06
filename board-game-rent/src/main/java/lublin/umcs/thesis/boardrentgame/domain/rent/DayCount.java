package lublin.umcs.thesis.boardrentgame.domain.rent;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class DayCount {

  private final Long value;

  public DayCount(final Long value) {
    checkArgument(value != null, "Can't be null");
    checkArgument(value > 0, "Can't be negate");
    checkArgument(value < 30, "Can't be more than 30 days");
    this.value = value;
  }
}
