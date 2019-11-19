package lublin.umcs.thesis.boardrentgame.domain.rent;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
public class GameRentId {

  public final String value;

  public GameRentId(final String value) {
    checkArgument(!isNullOrEmpty(value), "GameRentId Can't be empty");
    this.value = value;
  }
}
