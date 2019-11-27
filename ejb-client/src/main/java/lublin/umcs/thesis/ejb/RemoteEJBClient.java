package lublin.umcs.thesis.ejb;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteEJBClient {

  public static void main(String[] args) throws Exception {
    invokeStatelessBean();
  }

  private static void invokeStatelessBean() throws NamingException {
    Properties p = new Properties();
    p.put("java.naming.factory.initial", "org.openejb.client.RemoteInitialContextFactory");
    p.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");

    InitialContext ic = new InitialContext(p);
    RentBoardGameServiceRemote rentBoardGameServiceRemote = (RentBoardGameServiceRemote) ic.lookup("RentBoardGameServiceRemote");
    ReturnBoardGameServiceRemote returnBoardGameServiceRemote = (ReturnBoardGameServiceRemote) ic.lookup("ReturnBoardGameService");

    // TODO: 11/27/19 VTE use
  }
}
