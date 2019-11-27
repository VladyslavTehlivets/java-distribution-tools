package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.rmi.api.RentBoardGameService;
import lublin.umcs.thesis.rmi.api.ReturnBoardGameService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class RmiClient {
  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry(1100);

      RentBoardGameService rentBoardGameService = (RentBoardGameService) registry.lookup("RentBoardGameService");
      ReturnBoardGameService returnBoardGameService = (ReturnBoardGameService) registry.lookup("ReturnBoardGameService");

      rentBoardGameService.rent("okjn", 1L, "PLN", Arrays.asList("Beautiful game"));
      returnBoardGameService.returnBoardGameService("sdfd", "PLN");

    } catch (Exception e) {
      System.err.println("RMI Exception");
      e.printStackTrace();
    }
  }
}
