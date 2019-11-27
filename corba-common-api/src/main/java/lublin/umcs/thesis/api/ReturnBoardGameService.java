package lublin.umcs.thesis.api;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReturnBoardGameService extends Remote {
	BigDecimal returnBoardGameService(String gameRentId, String priceCurrency) throws RemoteException;
}
