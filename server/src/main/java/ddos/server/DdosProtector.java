package ddos.server;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class DdosProtector {
	private final int permitsPerTime;
	private final long timeframeMillis;
	private volatile AtomicLong counter;
	private volatile long startMillis;
	
	public DdosProtector(int permitsPerTime, int timeframe, TimeUnit timeframeUnit) {
		this.permitsPerTime = permitsPerTime;
		this.timeframeMillis = TimeUnit.MILLISECONDS.convert(timeframe, timeframeUnit);
	}
	
	public boolean check() {
		long currentMillis = System.currentTimeMillis();
		
		if (shouldInitializeNewTimeframe(currentMillis)) {
			synchronized (this) {
				if (shouldInitializeNewTimeframe(currentMillis)) {
					startMillis = currentMillis;
					counter = new AtomicLong(0l);
				}
			}
		}
		
		return counter.addAndGet(1) <= permitsPerTime; 
	}
	
	private boolean shouldInitializeNewTimeframe(long currentMillis) {
		if (startMillis == 0l) {
			return true;
		}
		
		return ((currentMillis - startMillis) > timeframeMillis);
	}
}
