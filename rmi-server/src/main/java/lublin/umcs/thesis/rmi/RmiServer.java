package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.boardrentgame.domain.rent.RentDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentGameRepository;
import lublin.umcs.thesis.boardrentgame.domain.rent.RentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.rent.ReturnRentPriceDomainService;
import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.gamerent.GameRentRepository;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;
import lublin.umcs.thesis.rmi.api.RentBoardGameService;
import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;
import lublin.umcs.thesis.rmi.apiimplementation.RentBoardGameServiceImpl;
import lublin.umcs.thesis.rmi.apiimplementation.ReturnBoardGameServiceImpl;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.boardgame.BoardGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.infrastructure.gamerent.GameRentJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentGameDomainService;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentGameJpaRepository;
import lublin.umcs.thesis.rmi.boardrentgame.rent.RentPriceService;
import lublin.umcs.thesis.rmi.boardrentgame.rent.ReturnRentPriceService;
import lublin.umcs.thesis.rmi.boardrentgame.user.UserJpaRepository;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

  public static void main(String[] args) {
    try {
      BoardGameJpaRepository boardGameJpaRepository = new BoardGameJpaRepository();
      RentGameRepository rentGameRepository = new RentGameJpaRepository();
      DomainUserRepository domainUserRepository = new UserJpaRepository();
      RentPriceDomainService rentPriceDomainService = new RentPriceService(domainUserRepository);
      RentDomainService rentDomainService = new RentGameDomainService(rentGameRepository, rentPriceDomainService);
      UserRepository userRepository = new UserJpaRepository();
      RentBoardGameService rentBoardGameService = new RentBoardGameServiceImpl(boardGameJpaRepository, rentDomainService, userRepository);
      RentBoardGameService rentBoardGameServiceStub = (RentBoardGameService) UnicastRemoteObject.exportObject(rentBoardGameService, 0);

      GameRentRepository gameRentRepository = new GameRentJpaRepository();
      ReturnRentPriceDomainService returnRentPriceDomainService = new ReturnRentPriceService(domainUserRepository);
      ReturnBoardGameService returnBoardGameService = new ReturnBoardGameServiceImpl(gameRentRepository, returnRentPriceDomainService);
      ReturnBoardGameService returnBoardGameServiceStub = (ReturnBoardGameService) UnicastRemoteObject.exportObject(returnBoardGameService, 0);

      Registry registry = LocateRegistry.createRegistry(1100);
      registry.rebind("RentBoardGameService", rentBoardGameServiceStub);
      registry.rebind("ReturnBoardGameService", returnBoardGameServiceStub);
      System.out.println("Services bound");
    } catch (Exception e) {
      System.err.println("Business services exception:");
      e.printStackTrace();
    }
  }
}
