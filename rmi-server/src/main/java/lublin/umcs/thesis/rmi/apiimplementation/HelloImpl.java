package lublin.umcs.thesis.rmi.apiimplementation;

import lublin.umcs.thesis.rmi.api.HelloInterface;

import java.rmi.RemoteException;

public class HelloImpl implements HelloInterface {

  public void sayHello(String from) throws RemoteException {
    System.out.println("Hello from " + from + "!!");
    System.out.flush();
  }
}
