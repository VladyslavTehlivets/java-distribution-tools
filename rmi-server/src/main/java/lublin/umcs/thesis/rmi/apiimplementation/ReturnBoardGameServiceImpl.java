package lublin.umcs.thesis.rmi.apiimplementation;

import lombok.RequiredArgsConstructor;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;
import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.Settlement;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;
import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class ReturnBoardGameServiceImpl implements ReturnBoardGameService {
	private final GameRentRepository gameRentRepository;
	private final ReturnRentPriceDomainService returnRentPriceService;

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
