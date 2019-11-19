package lublin.umcs.thesis.ejb.boardgame.returngame;

import javax.ejb.Remote;
import java.math.BigDecimal;

@Remote
public interface ReturnBoardGameServiceRemote {
	BigDecimal returnBoardGameService(String gameRentId, String priceCurrency);
}
