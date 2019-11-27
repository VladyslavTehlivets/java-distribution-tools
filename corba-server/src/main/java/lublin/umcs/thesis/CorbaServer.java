package lublin.umcs.thesis;

import lublin.umcs.thesis.api.RentBoardGameService;
import lublin.umcs.thesis.api.ReturnBoardGameService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class CorbaServer {

  public static final EntityManager ENTITY_MANAGER = Persistence.createEntityManagerFactory("boardgame").createEntityManager();

  public static void main(String[] args) {
    try {
      RentBoardGameService rentBoardGameService = RentBoardGameServiceFactory.getSingletonInstance();
      ReturnBoardGameService returnBoardGameService = ReturnBoardGameServiceFactory.getSingletonInstance();

      Context context = new InitialContext();
      context.rebind("RentBoardGameService", rentBoardGameService);
      context.rebind("ReturnBoardGameService", returnBoardGameService);

      System.out.println("Corba Server is Ready...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
