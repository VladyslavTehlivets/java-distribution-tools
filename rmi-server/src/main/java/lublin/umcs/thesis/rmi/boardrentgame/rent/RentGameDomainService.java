package lublin.umcs.thesis.rmi.boardrentgame.rent;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class RentGameDomainService implements RentDomainService {

	private final RentGameRepository rentGameRepository;
	private final RentPriceDomainService rentPriceService;

	@Override
	public boolean isPossibleToRealizeRentByUser(final GameRent gameRent, final User user) {
		return rentGameRepository.hasNoneUnfinishedRents(user);
	}

	@Override
	public BigDecimal rent(final GameRent gameRent, final PriceCurrency priceCurrency) {
		gameRent.rentGame();
		rentGameRepository.save(gameRent);
		return rentPriceService.countPrice(gameRent, priceCurrency).getValueAs(priceCurrency);
	}
}
