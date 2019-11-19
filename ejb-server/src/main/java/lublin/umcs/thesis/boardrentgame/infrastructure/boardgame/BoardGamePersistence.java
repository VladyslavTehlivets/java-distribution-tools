package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameDescription;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameName;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentPersistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

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
  @ManyToOne
  @JoinColumn(name = "GAME_RENT_ID")
  private GameRentPersistence gameRent;

  public BoardGamePersistence(BoardGame boardGame) {
    gameId = boardGame.getGameId().getValue();
    valueAs = boardGame.getGamePrice().getValueAs(PriceCurrency.PLN);
    name = boardGame.getName().getValue();
    gameDescription = boardGame.getGameDescription().getValue();
  }

  public BoardGame toBoardGame() {
    return BoardGame.builder()
            .gameId(new GameId(gameId))
            .gameDescription(new GameDescription(gameDescription))
            .gamePrice(new Price(valueAs, PriceCurrency.PLN))
            .name(new GameName(name))
            .gameRent(ofNullable(gameRent).map(GameRentPersistence::toGameRent).orElse(null))
            .build();
  }


}
