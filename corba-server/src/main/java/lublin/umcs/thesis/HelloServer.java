package lublin.umcs.thesis;

import lublin.umcs.thesis.apiimplementation.HelloImpl;

import javax.naming.Context;
import javax.naming.InitialContext;

public class HelloServer {
  public static void main(String[] args) {
    try {
      HelloImpl helloInterface = new HelloImpl();
      Context context = new InitialContext();
      context.rebind("HelloServer", helloInterface);
      System.out.println("Hello Server: Ready...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
