package de.springbootbuch.caching;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SynchedCachingDemoComponentTest {

	private static final Logger LOG = LoggerFactory
		.getLogger(SynchedCachingDemoComponentTest.class);

	@Autowired
	private SynchedCachingDemoComponent syncedCaching;

	@Test
	public void demonstrateCachingWithouthSync() throws InterruptedException {
		int num = 4;

		ExecutorService executor = Executors.newFixedThreadPool(num);
		final CountDownLatch latch = new CountDownLatch(num);
		Stream.generate(() -> new Runnable() {
			@Override
			public void run() {

				try {
					final int res = syncedCaching.computeSomething();
					LOG.info("Result was {}", res);
				} catch (InterruptedException ex) {
				} finally {
					latch.countDown();
				}
			}
		}).limit(num).parallel().forEach(executor::submit);
		latch.await();
		assertThat(syncedCaching.getCounterDefault().get()).isGreaterThan(1);
	}
	
	@Test
	public void demonstrateCachingWithSync() throws InterruptedException {
		int num = 4;

		ExecutorService executor = Executors.newFixedThreadPool(num);
		final CountDownLatch latch = new CountDownLatch(num);
		Stream.generate(() -> new Runnable() {
			@Override
			public void run() {
				
				try {
					final int res = syncedCaching.computeSomethingSynced();
					LOG.info("Result was {}", res);
				} catch (InterruptedException ex) {
				} finally {
					latch.countDown();
				}
			}
		}).limit(num).parallel().forEach(executor::submit);
		latch.await();
		assertThat(syncedCaching.getCounterSynced().get()).isEqualTo(1);
	}

}
