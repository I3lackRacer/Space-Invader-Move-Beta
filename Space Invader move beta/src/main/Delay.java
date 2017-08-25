package main;

public class Delay {

	public Delay(int millisekunden) {
		double endtime = System.currentTimeMillis() + millisekunden;
		System.out.println("Wait for: " + millisekunden);

		while (System.currentTimeMillis() <= endtime)
			;
		System.out.println("Finish");
	}

}
