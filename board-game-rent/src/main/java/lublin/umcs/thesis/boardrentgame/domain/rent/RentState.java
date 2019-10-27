package lublin.umcs.thesis.boardrentgame.domain.rent;

import java.util.Arrays;
import java.util.List;

public enum RentState {
  DRAFT,
  RENT,
  RETURNED,
  EXPIRED;

  public static List<RentState> unfinishedStates() {
    return Arrays.asList(EXPIRED, RENT, DRAFT);
  }
}
