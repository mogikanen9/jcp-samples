package org.mogikabebsoftware.play.sem;

public class MainRun {

	public static void main(String[] args) {

		Pool printerPool = new Pool();
		printerPool.init();
		printerPool.info();
		
		for (int i = 0; i < 100; i++) {
			final String valueToPrint = "Sending to printer value->%s" + i;
			new Thread(() -> {
				Printer printer = null;
				try {
					printer = printerPool.getPrinter();
					printer.printValue(valueToPrint);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					printerPool.releasePrinter(printer);
				}
			}).start();
		}				

	}

}
