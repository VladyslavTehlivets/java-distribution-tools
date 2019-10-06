package lublin.umcs.thesis.ejb;

import javax.ejb.Remote;

@Remote
public interface Hello {
  void sayHello(String from);
}
