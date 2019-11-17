package lublin.umcs.thesis.boardrentgame.domain.user;

import com.google.common.base.Strings;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class UserName {

  private final String value;

  public UserName(final String value) {
    checkArgument(!Strings.isNullOrEmpty(value), "Can't be empty");
    this.value = value;
  }
}
