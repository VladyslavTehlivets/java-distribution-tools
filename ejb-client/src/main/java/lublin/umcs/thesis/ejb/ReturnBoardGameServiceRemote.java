package lublin.umcs.thesis.ejb;

import java.math.BigDecimal;

public interface ReturnBoardGameServiceRemote {
	BigDecimal returnBoardGameService(String gameRentId, String priceCurrency);
}
