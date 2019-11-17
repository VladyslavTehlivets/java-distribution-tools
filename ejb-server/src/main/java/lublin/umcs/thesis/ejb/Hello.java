package lublin.umcs.thesis.ejb;

import javax.ejb.Remote;

@Remote
public interface Hello {
  String sayHello(String from);
}
