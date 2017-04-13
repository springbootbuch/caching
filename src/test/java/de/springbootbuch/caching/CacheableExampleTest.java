package de.springbootbuch.caching;

import java.time.Year;
import java.util.Optional;
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
public class CacheableExampleTest {

	@Autowired
	private CacheableExample cachingExamples;

	@Test
	public void findFilmsShouldWork() {
		final AtomicInteger counter =
			cachingExamples.getFindCounter();

		Optional<Film> result;
		result = cachingExamples.findFilm("test1");
		assertThat(result.isPresent(), is(false));
		result = cachingExamples.findFilm("test2");
		assertThat(result.isPresent(), is(false));
		assertThat(counter.get(), is(2));

		result = cachingExamples.findFilm("Star Wars: A New Hope");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(3));
		result = cachingExamples.findFilm("Star Wars: A New Hope");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(4));

		result = cachingExamples.findFilm("Sharks");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(5));
		result = cachingExamples.findFilm("Sharks");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(6));

		result = cachingExamples.findFilm("Twins");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(7));
		result = cachingExamples.findFilm("Twins");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(7));
		
		cachingExamples.udpdateFilm("Twins");
		result = cachingExamples.findFilm("Twins");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(8));
		
		cachingExamples.insertFilm("Alien", Year.of(1979));
		result = cachingExamples.findFilm("Alien");
		assertThat(result.isPresent(), is(true));
		assertThat(counter.get(), is(8));
	}

}