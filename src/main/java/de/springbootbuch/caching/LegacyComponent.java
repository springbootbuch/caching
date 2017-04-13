package de.springbootbuch.caching;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class LegacyComponent {

	private final AtomicInteger findCounter
		= new AtomicInteger();

	public List<Film> findAllFilms(final Year releaseYear) {
		final List<Film> rv = new ArrayList<>();
		if (releaseYear.getValue() == 1991) {
			rv.add(new Film("Terminator II", releaseYear));
		}
		findCounter.incrementAndGet();
		return rv;
	}

	public AtomicInteger getFindCounter() {
		return findCounter;
	}
}
