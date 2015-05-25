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
		double mmolit, dg;
		mmolit_list = new ArrayList<Double>();
		values = input.getValues();
//		System.out.println(values.toString());
				
		for(int count = 2; count < values.size(); count+=3){
			
			//System.out.println("count = "+count+"\\\\ values size = "+values.size());			
			mmolit = Voter.getMmmoLit(count-29, count, values);
			mmolit_list.add(mmolit);
		    
			System.out.println(lastThree());
			if(mmolit < 6)
				System.out.println("NOT INJECTING (g < 6) "+mmolit);
			else{
				dg = findDG(mmolit_list.size()<3?mmolit_list.size():3);
				if(dg < -0.4)
					System.out.println("NOT INJECTING (dg < -0.4) "+dg);
				else
					System.out.println("INJECTING (dg > -0.4) "+dg);
			}
			
		}
		
	}
	
	private static String lastThree(){
		int size = mmolit_list.size();
		if(size == 1)
			return "\nlast three: " + mmolit_list.get(0);
		if(size == 2)
			return "\nlast three: " + mmolit_list.get(0) +" -> "+ mmolit_list.get(1);
		if(size > 2)
			return "\nlast three: " + mmolit_list.get(size-3)  +" -> "+ mmolit_list.get(size-2)  +" -> "+ mmolit_list.get(size-1);
		
		return  null;
		
	}

	private static double findDG(int num) {
		int size = mmolit_list.size();
		ArrayList<Double> arr = new ArrayList<Double>();
		double sum = 0;
		
		if (size == 1)
			return mmolit_list.get(0);
		
		for(int i=size-num; i<size-1; i++)
			arr.add( (mmolit_list.get(i+1)-mmolit_list.get(i)) );
		
		for(int i=0; i<arr.size(); i++)
			sum += arr.get(i);
			//sum += mmolit_list.get(i);
		
		return (sum/arr.size());
			
	}
	



}
