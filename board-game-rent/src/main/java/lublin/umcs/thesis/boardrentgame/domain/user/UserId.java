package lublin.umcs.thesis.boardrentgame.domain.user;

import com.google.common.base.Strings;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class UserId {

  private final String value;

  public UserId(final String value) {
    checkArgument(Strings.isNullOrEmpty(value), "Can't be empty");
    this.value = value;
  }
}
