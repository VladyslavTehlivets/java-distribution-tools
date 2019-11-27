package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.rmi.api.RentBoardGameService;
import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

  public static final EntityManager ENTITY_MANAGER = Persistence.createEntityManagerFactory("boardgame").createEntityManager();

  public static void main(String[] args) {
    try {

      RentBoardGameService rentBoardGameService = RentBoardGameServiceFactory.createServiceInstance();
      RentBoardGameService rentBoardGameServiceStub = (RentBoardGameService) UnicastRemoteObject.exportObject(rentBoardGameService, 0);

      ReturnBoardGameService returnBoardGameService = ReturnBoardGameServiceFactory.createServiceInstance();
      ReturnBoardGameService returnBoardGameServiceStub = (ReturnBoardGameService) UnicastRemoteObject.exportObject(returnBoardGameService, 0);

      Registry registry = LocateRegistry.createRegistry(1100);
      registry.rebind("RentBoardGameService", rentBoardGameServiceStub);
      registry.rebind("ReturnBoardGameService", returnBoardGameServiceStub);
    } catch (Exception e) {
      System.err.println("Business services exception:");
      e.printStackTrace();
    }
  }
}
