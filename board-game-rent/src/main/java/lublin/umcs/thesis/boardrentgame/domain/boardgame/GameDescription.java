package lublin.umcs.thesis.boardrentgame.domain.boardgame;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Getter
public class GameDescription {

  private final String value;

  public GameDescription(final String value) {
    checkArgument(isNullOrEmpty(value), "Description can't be empty");
    checkArgument(value.length() < 2000, "Description mus be less than 2000 characters");
    this.value = value;
  }
}
