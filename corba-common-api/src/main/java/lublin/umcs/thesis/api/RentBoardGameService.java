package lublin.umcs.thesis.api;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;

public interface RentBoardGameService {

	BigDecimal rent(String userId, Long dayCount, String priceCurrency, List<String> gameNames) throws OperationNotSupportedException;
}
