package lublin.umcs.thesis.boardrentgame.infrastructure.gamerent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameDescription;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameId;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.GameName;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.DayCount;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentState;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGamePersistence;
import lublin.umcs.thesis.boardrentgame.user.UserPersistence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "GAME_RENT")
@Setter
@Getter
@NoArgsConstructor
public class GameRentPersistence {

  @Id private String gameRentId;

  @OneToOne(targetEntity = UserPersistence.class)
  @JoinColumn(name = "USER_ID", unique = true)
  private UserPersistence user;

  private LocalDate rentDay;
  private Long dayCount;
  private LocalDate returnDate;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameRent")
  private Set<BoardGamePersistence> games;

  @Enumerated(EnumType.STRING)
  private RentState rentState;

  public GameRentPersistence(GameRent gameRent) {
    user = new UserPersistence(gameRent.getUser());
    gameRentId = gameRent.getGameRentId().getValue();
    games = getBoardGames(gameRent);
    rentDay = gameRent.getRentDay();
    dayCount = gameRent.getDayCount().getValue();
    returnDate = gameRent.getReturnDay();
    rentState = gameRent.getRentState();
  }

  private Set<BoardGamePersistence> getBoardGames(final GameRent gameRent) {
    return gameRent.getBoardGames().stream()
        .map(BoardGamePersistence::new)
            .peek(boardGamePersistence -> boardGamePersistence.setGameRent(this))
        .collect(Collectors.toSet());
  }

  public GameRent toGameRent() {
    GameRent gameRent = GameRent.builder()
            .user(user.toUser())
            .gameRentId(new GameRentId(gameRentId))
            .rentDay(rentDay)
            .dayCount(new DayCount(dayCount))
            .returnDay(returnDate)
            .boardGames(extractBoardGames())
            .rentState(rentState)
            .build();
    gameRent.getBoardGames().forEach(boardGame -> boardGame.blockByRent(gameRent));
    return gameRent;
  }

  private Set<BoardGame> extractBoardGames() {
    return games.stream()
            .map(this::toBoardGame)
            .collect(Collectors.toSet());
  }

  private BoardGame toBoardGame(final BoardGamePersistence boardGamePersistence) {
    return BoardGame.builder()
            .gameId(new GameId(boardGamePersistence.getGameId()))
            .gameDescription(new GameDescription(boardGamePersistence.getGameDescription()))
            .gamePrice(new Price(boardGamePersistence.getValueAs(), PriceCurrency.PLN))
            .name(new GameName(boardGamePersistence.getName()))
            .build();
  }

}
