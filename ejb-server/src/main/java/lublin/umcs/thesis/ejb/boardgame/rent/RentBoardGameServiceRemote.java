package lublin.umcs.thesis.ejb.boardgame.rent;

import javax.ejb.Remote;
import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface RentBoardGameServiceRemote {

	BigDecimal rent(String userId, Long dayCount, String priceCurrency, List<String> gameNames) throws OperationNotSupportedException;
}
