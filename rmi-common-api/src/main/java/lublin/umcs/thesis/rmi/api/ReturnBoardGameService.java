package lublin.umcs.thesis.rmi.api;

import java.math.BigDecimal;
import java.rmi.Remote;

public interface ReturnBoardGameService extends Remote {
	BigDecimal returnBoardGameService(String gameRentId, String priceCurrency);
}
