package lublin.umcs.thesis.api;

import java.math.BigDecimal;

public interface ReturnBoardGameService {
	BigDecimal returnBoardGameService(String gameRentId, String priceCurrency);
}
