package de.springbootbuch.caching;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import java.time.Year;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.assertj.core.api.Assertions;
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
public class CacheableExampleTest {

	@Autowired
	private CacheableExample cachingExamples;

	@Test
	public void findFilmsShouldWork() {
		final AtomicInteger counter =
			cachingExamples.getFindCounter();

		Optional<Film> result;
		result = cachingExamples.findFilm("test1");
		assertThat(result.isPresent()).isFalse();
		result = cachingExamples.findFilm("test2");
		assertThat(result.isPresent()).isFalse();
		assertThat(counter.get()).isEqualTo(2);

		result = cachingExamples.findFilm("Star Wars: A New Hope");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(3);
		result = cachingExamples.findFilm("Star Wars: A New Hope");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(4);

		result = cachingExamples.findFilm("Sharks");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(5);
		result = cachingExamples.findFilm("Sharks");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(6);

		result = cachingExamples.findFilm("Twins");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(7);
		result = cachingExamples.findFilm("Twins");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(7);
		
		cachingExamples.udpdateFilm("Twins");
		result = cachingExamples.findFilm("Twins");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(8);
		
		cachingExamples.insertFilm("Alien", Year.of(1979));
		result = cachingExamples.findFilm("Alien");
		assertThat(result.isPresent()).isTrue();
		assertThat(counter.get()).isEqualTo(8);
	}

}
