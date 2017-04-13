package de.springbootbuch.caching;

import java.time.Year;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LegacyComponentTest {

	@Autowired
	private LegacyComponent legacyComponent;

	@Test
	public void findFilmsShouldWork() {
		final AtomicInteger counter = legacyComponent.getFindCounter();

		List<Film> results;
		results = legacyComponent.findAllFilms(Year.of(1991));
		assertThat(results.size(), is(1));
		assertThat(counter.get(), is(1));

		results = legacyComponent.findAllFilms(Year.of(1991));
		assertThat(results.size(), is(1));
		assertThat(counter.get(), is(1));
	}
}
