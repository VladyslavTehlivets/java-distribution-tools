package lublin.umcs.thesis.boardrentgame.user;

import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

public class UserJpaRepository implements DomainUserRepository, UserRepository {

	@PersistenceUnit(unitName = "boardgame")
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void save(final User user) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.merge(new UserPersistence(user));

		entityTransaction.commit();
	}

	@Override
	public boolean isUserWithRebate(final User user) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		boolean result = entityManager.find(UserPersistence.class, user.getUserId().getValue())
				.getUserId()
				.equals("10");

		entityTransaction.commit();

		return result;
	}

	@Override public User loadById(UserId userId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		User user = entityManager.find(UserPersistence.class, userId.getValue())
				.toUser();

		entityTransaction.commit();

		return user;
	}
}
