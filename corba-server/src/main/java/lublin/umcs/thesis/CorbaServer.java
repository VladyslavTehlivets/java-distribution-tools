package lublin.umcs.thesis;

import lublin.umcs.thesis.api.RentBoardGameService;
import lublin.umcs.thesis.api.ReturnBoardGameService;
import lublin.umcs.thesis.apiimplementation.RentBoardGameServiceImpl;
import lublin.umcs.thesis.apiimplementation.ReturnBoardGameServiceImpl;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentJpaRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;
import lublin.umcs.thesis.boardrentgame.rent.RentGameDomainService;
import lublin.umcs.thesis.boardrentgame.rent.RentGameJpaRepository;
import lublin.umcs.thesis.boardrentgame.rent.RentPriceService;
import lublin.umcs.thesis.boardrentgame.rent.ReturnRentPriceService;
import lublin.umcs.thesis.boardrentgame.user.UserJpaRepository;

import javax.naming.Context;
import javax.naming.InitialContext;

public class CorbaServer {
  public static void main(String[] args) {
    try {
      BoardGameJpaRepository boardGameJpaRepository = new BoardGameJpaRepository();
      RentGameRepository rentGameRepository = new RentGameJpaRepository();
      DomainUserRepository domainUserRepository = new UserJpaRepository();
      RentPriceDomainService rentPriceDomainService = new RentPriceService(domainUserRepository);
      RentDomainService rentDomainService = new RentGameDomainService(rentGameRepository, rentPriceDomainService);
      UserRepository userRepository = new UserJpaRepository();
      RentBoardGameService rentBoardGameService = new RentBoardGameServiceImpl(boardGameJpaRepository, rentDomainService, userRepository);

      GameRentRepository gameRentRepository = new GameRentJpaRepository();
      ReturnRentPriceDomainService returnRentPriceDomainService = new ReturnRentPriceService(domainUserRepository);
      ReturnBoardGameService returnBoardGameService = new ReturnBoardGameServiceImpl(gameRentRepository, returnRentPriceDomainService);

      Context context = new InitialContext();
      context.rebind("RentBoardGameService", rentBoardGameService);
      context.rebind("ReturnBoardGameService", returnBoardGameService);

      System.out.println("Corba Server is Ready...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
