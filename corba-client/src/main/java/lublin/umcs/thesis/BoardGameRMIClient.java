package lublin.umcs.thesis;

import lublin.umcs.thesis.api.RentBoardGameService;
import lublin.umcs.thesis.api.ReturnBoardGameService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.util.Arrays;

public class BoardGameRMIClient {

  public static void main(String[] args) {

    try {
      Context ic = new InitialContext();

      // STEP 1: Get the Object reference from the Name Service
      // using JNDI call.
      Object rentBoardGameServiceRef = ic.lookup("RentBoardGameService");
      System.out.println("Client: Obtained a ref. to RentBoardGameService.");

      Object returnBoardGameServiceRef = ic.lookup("ReturnBoardGameService");
      System.out.println("Client: Obtained a ref. to ReturnBoardGameService.");
      // STEP 2: Narrow the object reference to the concrete type and
      // invoke the method.
      RentBoardGameService rentBoardGameService =
              (RentBoardGameService)
                      PortableRemoteObject.narrow(rentBoardGameServiceRef, RentBoardGameService.class);

      ReturnBoardGameService returnBoardGameService =
              (ReturnBoardGameService) PortableRemoteObject.narrow(returnBoardGameServiceRef, ReturnBoardGameService.class);

      rentBoardGameService.rent("", 1L, "PLN", Arrays.asList("beautiful game"));
      returnBoardGameService.returnBoardGameService("", "PLN");

    } catch (Exception e) {
      System.err.println("Exception " + e + "Caught");
      e.printStackTrace();
    }
  }
}
