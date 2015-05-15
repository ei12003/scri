package scri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Voter {

	public static double getMmmoLit(int start, int end,
			ArrayList<SensorValues> values) {
		ArrayList<Double> arr;
		double median;

		arr = getArraySorted(start, end, values);
		Collections.sort(arr);

		median = findMedian(arr);

		return median;
	}

	private static double findMedian(ArrayList<Double> arr) {
		
		double median = 0;
		double pos1 = Math.floor((arr.size() - 1.0) / 2.0);
		double pos2 = Math.ceil((arr.size() - 1.0) / 2.0);
		
		if (pos1 == pos2){
			median = arr.get((int) pos1);
		}
		else{
			median = (arr.get((int) pos1) + arr.get((int) pos2)) / 2.0;
		}
		System.out.println(arr.toString());
		System.out.println("mmol/litro: " + median);
		System.out.println("########");
		return median;
	}

	private static ArrayList<Double> getArraySorted(int start, int end,
			ArrayList<SensorValues> values) {

		ArrayList<Double> arr = new ArrayList<Double>();
		int arr_index = 0;

		if (end > values.size()) {
			end = values.size();
			start = end - 29;
		}
		if (start < 0)
			start = 0;

		for (int count = start; count <= end; count++) {
			double s1 = values.get(count).getSensor1(), s2 = values.get(count)
					.getSensor2();
			if (s1 >= -3.4)
				arr.add(values.get(count).getSensor1());
			if (s2 >= -3.4)
				arr.add(values.get(count).getSensor2());
		}

		return arr;
	}

}
