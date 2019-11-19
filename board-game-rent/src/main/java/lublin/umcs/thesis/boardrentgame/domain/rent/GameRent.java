package lublin.umcs.thesis.boardrentgame.domain.rent;

import lombok.Builder;
import lombok.Getter;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

@Getter
public class GameRent {

  private final GameRentId gameRentId;
  private final Set<BoardGame> boardGames;
  private final User user;
  private final LocalDate rentDay;
  private final DayCount dayCount;
  private LocalDate returnDay;
  private RentState rentState;

  @Builder
  public GameRent(
      final GameRentId gameRentId,
      final Set<BoardGame> boardGames,
      final User user,
      final RentState rentState,
      final LocalDate rentDay,
      final LocalDate returnDay,
      final DayCount dayCount) {
    this.gameRentId = requireNonNull(gameRentId, "GameRentId can't be null");
    this.boardGames = requireNonNull(boardGames, "GameId can't be null");
    checkArgument(boardGames.size() < 6, "Can't rent more than 5 games");
    this.user = requireNonNull(user, "UserName can't be null");
    this.rentState = requireNonNull(rentState, "RentState can't be null");
    this.rentDay = requireNonNull(rentDay, "RentDay can't be null");
    this.dayCount = requireNonNull(dayCount, "DayCount can't be null");
    this.returnDay = returnDay;
  }

  public Settlement returnToStock() {
    this.returnDay = LocalDate.now();
    final int rentPeriod = Period.between(rentDay, returnDay).getDays();
    if (rentPeriod > dayCount.getValue()) {
      this.rentState = RentState.EXPIRED;
      this.boardGames.forEach(BoardGame::unBlock);
      return new Settlement(Settlement.SettlementSide.USER, rentPeriod - dayCount.getValue());
    } else {
      this.rentState = RentState.RETURNED;
      this.boardGames.forEach(BoardGame::unBlock);
      return new Settlement(Settlement.SettlementSide.SYSTEM, dayCount.getValue() - rentPeriod);
    }
  }

  public void rentGame() {
    this.rentState = RentState.RENT;
    this.boardGames.forEach(boardGame -> boardGame.blockByRent(this));
  }
}
