package lublin.umcs.thesis.boardrentgame.domain.rent;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
// TODO: 10/6/19 component
public class GameRentFactory {

  public static GameRent create(final User user, final List<BoardGame> games, final Long dayCount) {

    return GameRent.builder()
        .dayCount(new DayCount(dayCount))
        .boardGames(new HashSet<>(games))
        .gameRentId(new GameRentId(UUID.randomUUID().toString()))
        .rentDay(LocalDate.now())
        .rentState(RentState.DRAFT)
        .user(user)
        .build();
  }
}
