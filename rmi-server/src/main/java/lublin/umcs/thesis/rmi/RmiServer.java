package lublin.umcs.thesis.rmi;

import lublin.umcs.thesis.rmi.api.HelloInterface;
import lublin.umcs.thesis.rmi.apiimplementation.HelloImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {

  public static void main(String[] args) {
    try {
      HelloInterface helloImpl = new HelloImpl();
      HelloInterface stub = (HelloInterface) UnicastRemoteObject.exportObject(helloImpl, 0);
      Registry registry = LocateRegistry.createRegistry(1100);
      registry.rebind("HelloServer", stub);
      System.out.println("HelloInterface bound");
    } catch (Exception e) {
      System.err.println("HelloInterface exception:");
      e.printStackTrace();
    }
  }
}
