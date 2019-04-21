package org.mogikabebsoftware.play.sem;

public class Printer {

	private String name;

	public Printer(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void printValue(final String value) {
		new Thread(() -> {
			try {				
				Thread.sleep(50);
				System.out.println(String.format("Printing value->%s", value));
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
}
