package lublin.umcs.thesis.ejb.boardgame.returngame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRent;
import lublin.umcs.thesis.boardrentgame.domain.rent.GameRentId;
import lublin.umcs.thesis.boardrentgame.rent.ReturnRentPriceService;
import lublin.umcs.thesis.boardrentgame.domain.rent.Settlement;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Objects;

@Stateless
public class ReturnBoardGameService implements ReturnBoardGameServiceRemote {

	@EJB
	private GameRentRepository gameRentRepository;

	@EJB
	private ReturnRentPriceService returnRentPriceService;

	@Override public Price returnBoardGameService(String gameRentId, String priceCurrency) {

		GameRent gameRent = gameRentRepository.loadGameRentId(new GameRentId(gameRentId));

		final Settlement settlement = gameRent.returnToStock();
		gameRentRepository.save(gameRent);

		final Price price =
				returnRentPriceService.countPrice(
						gameRent, PriceCurrency.valueOf(priceCurrency), settlement.getDays());
		if (Objects.equals(Settlement.SettlementSide.USER, settlement.getPayTo())) {
			return price;
		}
		return price.negate(PriceCurrency.valueOf(priceCurrency));
	}
}
