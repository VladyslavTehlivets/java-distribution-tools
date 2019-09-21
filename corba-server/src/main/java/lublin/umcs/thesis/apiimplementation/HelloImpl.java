package lublin.umcs.thesis.apiimplementation;

import lublin.umcs.thesis.api.HelloInterface;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class HelloImpl extends PortableRemoteObject implements HelloInterface {
  public HelloImpl() throws RemoteException {
    super(); // invoke rmi linking and remote object initialization
  }

  public void sayHello(String from) throws RemoteException {
    System.out.println("Hello from " + from + "!!");
    System.out.flush();
  }
}
