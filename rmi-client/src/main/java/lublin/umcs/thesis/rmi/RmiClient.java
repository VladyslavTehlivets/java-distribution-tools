package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.rmi.api.HelloInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {
  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry(1100);
      HelloInterface comp = (HelloInterface) registry.lookup("HelloServer");
      comp.sayHello("Yeeah");
    } catch (Exception e) {
      System.err.println("HelloInterface exception:");
      e.printStackTrace();
    }
  }
}
