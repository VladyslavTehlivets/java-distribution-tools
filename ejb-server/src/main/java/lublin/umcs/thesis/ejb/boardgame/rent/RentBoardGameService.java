package lublin.umcs.thesis.ejb.boardgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentFactory;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.OperationNotSupportedException;
import java.util.List;

@Stateless
public class RentBoardGameService implements RentBoardGameServiceRemote {

	@EJB
	private BoardGameRepository boardGameRepository;
	@EJB
	private RentDomainService rentDomainService;
	@EJB
	private UserRepository userRepository;

	@Override public Price rent(String userId, Long dayCount, String priceCurrency, String... gameNames) throws OperationNotSupportedException {
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
