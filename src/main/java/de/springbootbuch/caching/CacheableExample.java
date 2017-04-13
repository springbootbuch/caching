package de.springbootbuch.caching;

import java.time.Year;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class CacheableExample {
	private final AtomicInteger findCounter
		= new AtomicInteger();

	@Cacheable(
		cacheNames = "films", 
		condition = "#title.length() < 16",
		unless = "#result?.releaseYear?.value < 1976")
	public Optional<Film> findFilm(final String title) {
		Film film = null;
		if("Star Wars: A New Hope".equals(title)) {
			film = new Film(title, Year.of(1977));
		} else if ("Sharks".equals(title)) {
			film = new Film(title, Year.of(1975));
		} else if ("Twins".equals(title)) {
			film = new Film(title, Year.of(1988));
		}
		findCounter.incrementAndGet();
		return Optional.ofNullable(film);
	}

	public AtomicInteger getFindCounter() {
		return findCounter;
	}	
}
