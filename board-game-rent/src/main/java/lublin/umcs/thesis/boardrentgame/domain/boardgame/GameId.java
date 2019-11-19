package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
public class GameId {

  private final String value;

  public GameId(final String value) {
    checkArgument(!isNullOrEmpty(value), "Can't be empty");
    this.value = value;
  }
}
