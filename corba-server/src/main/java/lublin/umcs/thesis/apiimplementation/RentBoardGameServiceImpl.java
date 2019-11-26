package lublin.umcs.thesis.apiimplementation;

import lublin.umcs.thesis.api.RentBoardGameService;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.BoardGame;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentFactory;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;

import javax.naming.OperationNotSupportedException;
import javax.rmi.PortableRemoteObject;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

public class RentBoardGameServiceImpl extends PortableRemoteObject implements RentBoardGameService {
	private final BoardGameRepository boardGameRepository;
	private final RentDomainService rentDomainService;
	private final UserRepository userRepository;

	public RentBoardGameServiceImpl(final BoardGameRepository boardGameRepository, final RentDomainService rentDomainService,
			final UserRepository userRepository) throws RemoteException {
		super();
		this.boardGameRepository = boardGameRepository;
		this.rentDomainService = rentDomainService;
		this.userRepository = userRepository;
	}

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
