package org.mogikabebsoftware.play.sem;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author vladislav.voinov
 *
 */
public class Pool {

	private static final int POOL_SIZE = 2;

	private final Map<Printer, Boolean> printers = new ConcurrentHashMap<>();

	private final Semaphore available = new Semaphore(POOL_SIZE); // pool size

	public void init() {
		for (int i = 0; i < POOL_SIZE; i++) {
			String name = "printer" + i;
			printers.put(new Printer(name), Boolean.TRUE);
		}		
	}

	public Printer getPrinter() throws InterruptedException {
		available.acquire();
		System.out.println("Lock acquired");
		return getNextAvailableItem();
	}

	protected synchronized Printer getNextAvailableItem() {
		Entry<Printer, Boolean> pinterEntry = printers.entrySet().stream().filter((entry)->entry.getValue().equals(Boolean.TRUE)).findAny().get();
		pinterEntry.setValue(Boolean.FALSE);
		return pinterEntry.getKey();
	   }

	public void releasePrinter(Printer x) {		
		printers.replace(x, Boolean.FALSE, Boolean.TRUE);
		available.release();
		System.out.println("Lock released");
	}
	
	public void info() {
		System.out.println(String.format("# of printers in pool->%d",printers.keySet().size()));
	}
}
