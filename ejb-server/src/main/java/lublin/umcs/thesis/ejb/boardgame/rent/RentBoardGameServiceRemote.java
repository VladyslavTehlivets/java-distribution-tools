package lublin.umcs.thesis.ejb.boardgame.rent;

import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;

import javax.ejb.Remote;
import javax.naming.OperationNotSupportedException;

@Remote
public interface RentBoardGameServiceRemote {
	Price rent(String userId, Long dayCount, String priceCurrency, String... gameNames) throws OperationNotSupportedException;
}
