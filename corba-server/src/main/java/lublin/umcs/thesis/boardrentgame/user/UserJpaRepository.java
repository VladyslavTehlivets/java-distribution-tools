package lublin.umcs.thesis.boardrentgame.user;

import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;

import javax.persistence.EntityTransaction;

import static lublin.umcs.thesis.CorbaServer.ENTITY_MANAGER;

public class UserJpaRepository implements DomainUserRepository, UserRepository {

	@Override
	public void save(final User user) {
		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		ENTITY_MANAGER.merge(new UserPersistence(user));

		entityTransaction.commit();
	}

	@Override
	public boolean isUserWithRebate(final User user) {
		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		boolean result = ENTITY_MANAGER.find(UserPersistence.class, user.getUserId().getValue())
				.getUserId()
				.equals("10");

		entityTransaction.commit();

		return result;
	}

	@Override public User loadById(UserId userId) {
		EntityTransaction entityTransaction = ENTITY_MANAGER.getTransaction();
		entityTransaction.begin();

		User user = ENTITY_MANAGER.find(UserPersistence.class, userId.getValue())
				.toUser();

		entityTransaction.commit();

		return user;
	}
}
