package lublin.umcs.thesis.apiimplementation;

import lublin.umcs.thesis.api.ReturnBoardGameService;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;
import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.Settlement;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;

import javax.rmi.PortableRemoteObject;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Objects;

public class ReturnBoardGameServiceImpl extends PortableRemoteObject implements ReturnBoardGameService {
	private final GameRentRepository gameRentRepository;
	private final ReturnRentPriceDomainService returnRentPriceService;

	public ReturnBoardGameServiceImpl(final GameRentRepository gameRentRepository,
			final ReturnRentPriceDomainService returnRentPriceService) throws RemoteException {
		super();
		this.gameRentRepository = gameRentRepository;
		this.returnRentPriceService = returnRentPriceService;
	}

	@Override public BigDecimal returnBoardGameService(String gameRentId, String priceCurrency) {

		GameRent gameRent = gameRentRepository.loadGameRentId(new GameRentId(gameRentId));

		final Settlement settlement = gameRent.returnToStock();
		gameRentRepository.save(gameRent);

		final Price price =
				returnRentPriceService.countPrice(
						gameRent, PriceCurrency.valueOf(priceCurrency), settlement.getDays());
		if (Objects.equals(Settlement.SettlementSide.USER, settlement.getPayTo())) {
			return price.getValueAs(PriceCurrency.PLN);
		}
		return price.negate(PriceCurrency.valueOf(priceCurrency)).getValueAs(PriceCurrency.PLN);
	}
}
