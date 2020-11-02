package de.springbootbuch.caching;

import static org.assertj.core.api.Assertions.*;

import java.time.Year;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LegacyComponentTest {

	@Autowired
	private LegacyComponent legacyComponent;

	@Test
	public void findFilmsShouldWork() {
		final AtomicInteger counter = legacyComponent.getFindCounter();

		List<Film> results;
		results = legacyComponent.findAllFilms(Year.of(1991));
		assertThat(results.size()).isEqualTo(1);
		assertThat(counter.get()).isEqualTo(1);

		results = legacyComponent.findAllFilms(Year.of(1991));
		assertThat(results.size()).isEqualTo(1);
		assertThat(counter.get()).isEqualTo(1);
	}
}
