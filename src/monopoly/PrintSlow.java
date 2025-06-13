package monopoly;

public class PrintSlow {

	// To print to Console slower than Default and at a specific speed
	public void printSlow(String s) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(100);
			} catch (InterruptedException m) {
				;
			}
		}
	}

	// To print to Console slower than Default and at variable speed
	public void printSlow(String s, int t) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(t);
			} catch (InterruptedException m) {
				;
			}
		}
	}

	// To print to Console slower than Default and at variable speed with println()
	public void printSlowln(String s, int t) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(t);
			} catch (InterruptedException m) {
				;
			}
		}
		System.out.println();
	}

	// To print to Console slower than Default and at a specific speed with
	// println()
	public void printSlowln(String s) {
		for (int i = 0; i < s.length(); i++) {
			System.out.print("" + s.charAt(i));
			// sleep for a bit after printing a letter
			try {
				Thread.sleep(100);
			} catch (InterruptedException m) {
				;
			}
		}
		System.out.println();
	}

}
