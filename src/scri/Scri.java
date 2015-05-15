package scri;

import java.awt.List;
import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Scri {
	
	static ArrayList<SensorValues> values;
	static ArrayList<Double> mmolit_list;
	
	public static void main(String[] args) {
		ReadInput input = new ReadInput("input");
		double mmolit;
		mmolit_list = new ArrayList<Double>();
		values = input.getValues();
		System.out.println(values.toString());
				
		for(int count = 2; count < values.size(); count+=3){
			
			System.out.println("count = "+count+"\\\\ values size = "+values.size());			
			mmolit = Voter.getMmmoLit(count-29, count, values);
			mmolit_list.add(mmolit);
			isLowering();
			
			if(mmolit < 6 || isLowering())
				System.out.println("NOT INJECTING");
			else{
				
			}
			
		}
		
	}

	private static boolean isLowering() {
		
		int start = mmolit_list.size() - 10;
		ArrayList<Double> po = new ArrayList<Double>(); 
		
		if(mmolit_list.size() < 10)
			start = 0;
		
		for(int i = start;i<mmolit_list.size();i++){
			po.add(mmolit_list.get(i));
		}
		
		
		return false;
	}
	



}
