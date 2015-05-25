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
		if (arr != null) {

			Collections.sort(arr);

			median = findMedian(arr);
			return median;
		} else
			return -900;

	}

	private static double findMean(ArrayList<Double> arr) {
		double oi = 0;

		for (int i = 0; i < arr.size(); i++)
			oi += arr.get(i);

		return oi / (arr.size());
	}

	private static double findMedian(ArrayList<Double> arr) {

		double median = 0;
		double pos1 = Math.floor((arr.size() - 1.0) / 2.0);
		double pos2 = Math.ceil((arr.size() - 1.0) / 2.0);

		if (pos1 == pos2) {
			median = arr.get((int) pos1);
		} else {
			median = (arr.get((int) pos1) + arr.get((int) pos2)) / 2.0;
		}

		return median;
	}

	private static ArrayList<Double> getArraySorted(int start, int end,
			ArrayList<SensorValues> values) {

		ArrayList<Double> arr = new ArrayList<Double>();
		int arr_index = 0;
		int check = 0;
		int valid_values = 0;
		int count = 0;
		if (end > values.size()) {
			end = values.size();
			start = end - 29;
		}
		if (start < 0)
			start = 0;

		int check_last = end - 2;
		int end_ = end;
		try {
			for (count = start; count <= end; count++) {
				double s1 = values.get(count).getSensor1(), s2 = values.get(
						count).getSensor2();

				if (s1 >= -3.4) {
					arr.add(values.get(count).getSensor1());
					check++;
				}

				if (s2 >= -3.4) {
					arr.add(values.get(count).getSensor2());
					check++;
				}

				if (check == 0) {
					if (!(values.get(count).both_checked))
						end += 1;
					check = 0;
				} else if (count >= check_last)
					valid_values += 1;
				check = 0;

				if (valid_values < 3 && end_ == values.size() && end >= end_)
					return null;

			}

			return arr;
		} catch (Exception e) {
			return null;
		}
	}

}
