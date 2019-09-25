package lublin.umcs.thesis.ejb;

import javax.ejb.Stateless;

@Stateless
public class HelloImpl implements Hello {
  public void sayHello(String from) {
    System.out.println("Hello from " + from + "!!");
    System.out.flush();
  }
}
