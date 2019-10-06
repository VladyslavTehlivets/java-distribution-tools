package lublin.umcs.thesis.boardrentgame.domain.rent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Settlement {

  private final SettlementSide payTo;
  private final Long days;

  public enum SettlementSide {
    USER,
    SYSTEM
  }
}
