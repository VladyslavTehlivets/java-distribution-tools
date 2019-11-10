package lublin.umcs.thesis.boardrentgame.user;

import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;

public interface UserRepository {
	User loadById(UserId userId);
}
