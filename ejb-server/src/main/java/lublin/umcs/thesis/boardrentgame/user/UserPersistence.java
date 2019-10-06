package lublin.umcs.thesis.boardrentgame.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lublin.umcs.thesis.boardrentgame.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@Setter
@Getter
@NoArgsConstructor
public class UserPersistence {

  @Id private String userId;
  private String userName;

  public UserPersistence(User user) {
    userId = user.getUserId().getValue();
    userName = user.getUserName().getValue();
  }
}
