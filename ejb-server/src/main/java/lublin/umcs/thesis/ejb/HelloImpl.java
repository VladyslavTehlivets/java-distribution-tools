package lublin.umcs.thesis.ejb;

import javax.ejb.Stateless;

@Stateless
public class HelloImpl implements Hello {
  public String sayHello(String from) {
    return "Hello from " + from + "!!";
  }
}
