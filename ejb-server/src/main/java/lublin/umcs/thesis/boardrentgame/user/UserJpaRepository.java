package lublin.umcs.thesis.boardrentgame.user;

import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
@Local({ DomainUserRepository.class, UserRepository.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserJpaRepository implements DomainUserRepository, UserRepository {

  @PersistenceContext(unitName = "boardgame", type = PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;

  @Override
  public void save(final User user) {
    entityManager.persist(new UserPersistence(user));
  }

  @Override
  public boolean isUserWithRebate(final User user) {
    return entityManager.find(UserPersistence.class, user.getUserId().getValue())
            .getUserId()
            .equals("10");
  }

  @Override public User loadById(UserId userId) {
    return entityManager.find(UserPersistence.class, userId.getValue())
            .toUser();
  }
}
