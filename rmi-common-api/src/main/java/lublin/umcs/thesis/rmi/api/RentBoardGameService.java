package lublin.umcs.thesis.rmi.api;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.util.List;

public interface RentBoardGameService extends Remote {

	BigDecimal rent(String userId, Long dayCount, String priceCurrency, List<String> gameNames) throws OperationNotSupportedException;
}
