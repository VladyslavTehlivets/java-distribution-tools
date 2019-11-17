package lublin.umcs.thesis.boardrentgame.infrastructure.boardgame;

import com.google.common.collect.Lists;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.Price;
import lublin.umcs.thesis.boardrentgame.domain.boardgame.PriceCurrency;
import lublin.umcs.thesis.boardrentgame.domain.user.DomainUserRepository;
import lublin.umcs.thesis.boardrentgame.domain.user.User;
import lublin.umcs.thesis.boardrentgame.domain.user.UserId;
import lublin.umcs.thesis.boardrentgame.domain.user.UserName;
import lublin.umcs.thesis.boardrentgame.infrastructure.user.UserRepository;
import lublin.umcs.thesis.ejb.Hello;
import lublin.umcs.thesis.ejb.boardgame.rent.RentBoardGameServiceRemote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

class BoardGameJpaRepositoryTest {

	private static InitialContext initialContext;

	@BeforeAll
	public static void setUp() throws NamingException {
		Properties properties = new Properties();

		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.openejb.client.LocalInitialContextFactory");
		properties.put("log4j.category.OpenEJB.options ", " debug");
		properties.put("log4j.category.OpenEJB.startup ", " debug");
		properties.put("log4j.category.OpenEJB.startup.config ", " debug");
		properties.put("boardGame", "new://Resource?type=DataSource");
		properties.put("boardGame.JdbcDriver", "org.h2.Driver");
		properties.put("boardGame.JdbcUrl", "jdbc:h2:mem:boardGame");
		properties.put("boardGame.JtaManaged", "true");
		properties.put("boardgame.hibernate.show_sql", "true");
		properties.put("boardgame.hibernate.format_sql", "true");
		properties.put("boardgame.hibernate.hbm2ddl.auto", "update");

		properties.put("openejb.validation.output.level", "verbose");
		initialContext = new InitialContext(properties);
	}

	@Test
	void findGamesByNames() throws NamingException {
		final Hello hello = (Hello) initialContext.lookup("HelloImplRemote");
		System.out.println(hello.sayHello("Hello"));
	}

	@Test
	void findGamesByIds() throws NamingException {

		DomainUserRepository domainUserRepositoryLocal = (DomainUserRepository) initialContext.lookup("UserJpaRepositoryLocal");
		domainUserRepositoryLocal.save(new User(new UserId("11"), new UserName("Tehlivets")));

		RentBoardGameServiceRemote rentBoardGameServiceRemote = (RentBoardGameServiceRemote) initialContext.lookup("RentBoardGameServiceRemote");
		ArrayList<String> gameNames = new ArrayList<>();
		gameNames.add("example game");

		Price rentPrice = rentBoardGameServiceRemote.rent("11", 1L, "PLN", gameNames);

		Assertions.assertEquals(rentPrice.getValueAs(PriceCurrency.PLN), new BigDecimal(12));
	}
}