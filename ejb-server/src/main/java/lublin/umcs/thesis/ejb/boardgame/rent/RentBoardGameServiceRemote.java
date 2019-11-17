package lublin.umcs.thesis.ejb.boardgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;

import javax.ejb.Remote;
import javax.naming.OperationNotSupportedException;
import java.util.List;

@Remote
public interface RentBoardGameServiceRemote {

	Price rent(String userId, Long dayCount, String priceCurrency, List<String> gameNames) throws OperationNotSupportedException;
}
