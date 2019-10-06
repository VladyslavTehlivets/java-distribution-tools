package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "BOARD_GAME")
@Getter
@Setter
@NoArgsConstructor
public class BoardGamePersistence {

  @Id private String gameId;
  private String gameDescription;
  private BigDecimal valueAs;
  private String name;

  public BoardGamePersistence(BoardGame boardGame) {
    gameId = boardGame.getGameId().getValue();
    valueAs = boardGame.getGamePrice().getValueAs(PriceCurrency.PLN);
    name = boardGame.getName().getValue();
    gameDescription = boardGame.getGameDescription().getValue();
  }
}
