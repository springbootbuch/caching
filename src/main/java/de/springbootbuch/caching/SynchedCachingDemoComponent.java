package de.springbootbuch.caching;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SynchedCachingDemoComponent {
	private final AtomicInteger counterDefault 
		= new AtomicInteger();
	
	private final AtomicInteger counterSynced 
		= new AtomicInteger();
	
	@Cacheable(cacheNames = "synchedCachingDemo", key = "#root.methodName")
	public int computeSomething() throws InterruptedException {
		counterDefault.incrementAndGet();
		int nextInt = ThreadLocalRandom.current().nextInt(1000, 3000);
		Thread.sleep(nextInt);
		return nextInt;
	}
	
	@Cacheable(
		sync = true, 
		cacheNames = "synchedCachingDemo", 
		key = "#root.methodName")
	public int computeSomethingSynced() 
		throws InterruptedException {
		counterSynced.incrementAndGet();
		int nextInt = ThreadLocalRandom
			.current().nextInt(1000, 3000);
		Thread.sleep(nextInt);
		return nextInt;
	}
	
	public AtomicInteger getCounterDefault() {
		return counterDefault;
	}

	public AtomicInteger getCounterSynced() {
		return counterSynced;
	}
}
