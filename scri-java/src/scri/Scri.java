package scri;

import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class Scri {

	static ArrayList<SensorValues> values;
	static ArrayList<Double> g_list;
	static BufferedWriter bw;
	static double dg, ddg, ic, d;
	static int both, pastboth;
	static int entries;
	static String data;

	public static void main(String[] args) throws IOException {
		ReadInput input;
		Scanner a = new Scanner(System.in);
		if(args.length == 0)
			input = new ReadInput("input");
		else
			input = new ReadInput(args[0]);
		
		entries = 0;
		double g;
		both = 0;
		pastboth = 0;
		g_list = new ArrayList<Double>();
		values = input.getValues();
		ic = 0;
		bw = new BufferedWriter(new FileWriter("output"));
		data = "";
		for (int count = 2; count < values.size(); count += 3) {

			g = Voter.getMmmoLit(count - 29, count, values);
			try {

				both = bothFailed(values, count - 29, count);
				if (both > 0)
					count += both;
				pastboth += both;
				both = 0;
				if (pastboth >= 20) {
					log("FAIL");
					break;
				}

				if (g == -900)
					continue;
			} catch (Exception e) {
				log("FAIL" + e.toString());
				continue;
			}
			g_list.add(g);

			if (g_list.size() >= 10) {
				if (g < 6) {
					log("0");
					ic = 0.9 * ic;
				}

				else {

					findDGDDG(3);

					if (dg < -0.4) {

						log("0");
						ic = 0.9 * ic;
					}

					else {

						d = 0.8 * g + 0.2 * dg + 0.5 * ddg - ic;
						d = Math.floor(d);

						if (d < 0)
							d = 0;

						ic = d + 0.9 * ic;

						log(Integer.toString(Double.valueOf(d).intValue()));

					}
				}
				if (g_list.size() == 10)
					g_list.remove(0);
			}

			else

				log("WAIT");

		}
		bw.close();
		try {
			sendPost(data);
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("<> End <>");
		a.nextLine();
	}

	private static int bothFailed(ArrayList<SensorValues> values2, int start,
			int end) {

		int ctr = 0;
		int check = 0;

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
				check++;

			if (s2 >= -3.4)
				check++;

			if (check == 0) {

				if (!(values.get(count).both_checked)) {
					ctr++;

					values.get(count).both_checked = true;
				}

				check = 0;
			}
			check = 0;

		}

		return ctr;
	}

	private static String lastThree() {
		int size = g_list.size();
		if (size == 1)
			return "\nlast three: " + g_list.get(0);
		if (size == 2)
			return "\nlast three: " + g_list.get(0) + " -> " + g_list.get(1);
		if (size > 2)
			return "\nlast three: " + g_list.get(size - 3) + " -> "
					+ g_list.get(size - 2) + " -> " + g_list.get(size - 1);
		return null;

	}

	private static void log(String str) {

		entries++;

		try {

			System.out.println(str);
			bw.write(str + "\n");
			data += (str + "\n");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void findDGDDG(int num) {
		int size = g_list.size();
		ArrayList<Double> arr = new ArrayList<Double>();
		double sum = 0, x = -1, y = -1;

		for (int i = size - num; i < size - 1; i++) {

			arr.add((g_list.get(i + 1) - g_list.get(i)));
			if (i == size - num)
				x = (g_list.get(i + 1) - g_list.get(i));
			else
				y = (g_list.get(i + 1) - g_list.get(i));
		}

		for (int i = 0; i < arr.size(); i++)
			sum += arr.get(i);

		dg = sum / arr.size();
		ddg = (y - x) / 2;
	}

	private static void sendPost(String postData) throws Exception {

		String url = "http://localhost:8080/scri";
		String charset = "UTF-8";

		String query = String.format("data=%s",
				URLEncoder.encode(data, charset));

		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);

		try (OutputStream output = connection.getOutputStream()) {
			output.write(query.getBytes(charset));
		}

		InputStream response = connection.getInputStream();

		HttpURLConnection httpConnection = (HttpURLConnection) new URL(url)
				.openConnection();
		httpConnection.setRequestMethod("POST");

	}

}
