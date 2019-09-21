package lublin.umcs.thesis.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloInterface extends Remote {
  void sayHello(String from) throws RemoteException;
}
