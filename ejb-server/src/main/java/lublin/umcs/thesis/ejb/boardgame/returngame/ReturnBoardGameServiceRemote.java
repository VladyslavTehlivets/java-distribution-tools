package lublin.umcs.thesis.ejb.boardgame.returngame;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;

import javax.ejb.Remote;

@Remote
public interface ReturnBoardGameServiceRemote {
	Price returnBoardGameService(String gameRentId, String priceCurrency);
}
