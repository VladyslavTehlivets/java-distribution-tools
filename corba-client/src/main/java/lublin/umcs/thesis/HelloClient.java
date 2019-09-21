package lublin.umcs.thesis;

import lublin.umcs.thesis.api.HelloInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class HelloClient {

  public static void main(String[] args) {

    try {
      Context ic = new InitialContext();

      // STEP 1: Get the Object reference from the Name Service
      // using JNDI call.
      Object objRef = ic.lookup("HelloServer");
      System.out.println("Client: Obtained a ref. to Hello server.");

      // STEP 2: Narrow the object reference to the concrete type and
      // invoke the method.
      HelloInterface hi =
          (HelloInterface) PortableRemoteObject.narrow(objRef, HelloInterface.class);
      hi.sayHello(" MARS ");

    } catch (Exception e) {
      System.err.println("Exception " + e + "Caught");
      e.printStackTrace();
    }
  }
}
