package lublin.umcs.thesis.ejb;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;

public interface RentBoardGameServiceRemote {
	BigDecimal rent(String userId, Long dayCount, String priceCurrency, List<String> gameNames) throws OperationNotSupportedException;
}
