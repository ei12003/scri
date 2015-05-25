package scri;

public class SensorValues {

	double sensor1, sensor2;
	boolean both_checked;
	double i;

	public SensorValues(double i) {
		this.i = i;
		this.sensor1 = -3.5;
		this.sensor2 = -3.5;
		this.both_checked = false;
	}

	public double getSensor1() {
		return sensor1;
	}

	public void setSensor1(double sensor1) {
		this.sensor1 = -3.4 + 1.354 * sensor1 + 1.545
				* Math.tan(Math.pow(sensor1, (1.0 / 4.0)));
	}

	public double getSensor2() {
		return sensor2;
	}

	public void setSensor2(double sensor2) {

		this.sensor2 = -3.4 + 1.354 * sensor2 + 1.545
				* Math.tan(Math.pow(sensor2, (1.0 / 4.0)));

	}

}
