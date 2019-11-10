package lublin.umcs.thesis.boardrentgame.user;

import lublin.umcs.thesis.boardrentgame.domain.user.User;

public interface DomainUserRepository {
	void save(User user);

	boolean isUserWithRebate(final User user); // todo user with rents % 10 == 0
}
