package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
public class GameName {

  private final String value;

  public GameName(final String value) {
    checkArgument(isNullOrEmpty(value), "Can't be empty");
    this.value = value;
  }
}
