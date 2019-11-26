package lublin.umcs.thesis.rmi.apiimplementation;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentFactory;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;
import lublin.umcs.thesis.rmi.api.RentBoardGameService;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class RentBoardGameServiceImpl implements RentBoardGameService {
	private final BoardGameRepository boardGameRepository;
	private final RentDomainService rentDomainService;
	private final UserRepository userRepository;

	@Override public BigDecimal rent(String userId, Long dayCount, String priceCurrency, List<String> gameNames) throws OperationNotSupportedException {
		List<BoardGame> games = boardGameRepository.findGamesByNames(gameNames);

		User user = userRepository.loadById(new UserId(userId));
		GameRent gameRent = GameRentFactory.create(user, games, dayCount);

		if (rentDomainService.isPossibleToRealizeRentByUser(gameRent, gameRent.getUser())) {
			return rentDomainService.rent(gameRent, PriceCurrency.valueOf(priceCurrency));
		} else {
			throw new OperationNotSupportedException("Solve all previous rents");
		}
	}
}
