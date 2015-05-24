package scri;

import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Scri {
	
	static ArrayList<SensorValues> values;
	static ArrayList<Double> g_list;
	static BufferedWriter bw;
	static double dg,ddg,ic,d;
	
	
	public static void main(String[] args) throws IOException {
		ReadInput input = new ReadInput("input");
		double g;
		g_list = new ArrayList<Double>();
		values = input.getValues();
		ic = 0;
		bw = new BufferedWriter(new FileWriter("output"));
		
		for(int count = 2; count < values.size(); count+=3){
			
			//System.out.println("count = "+count+"\\\\ values size = "+values.size());			
			try {
				g = Voter.getMmmoLit(count-29, count, values);
			} catch(Exception e) {
				log("FAIL");
				continue;
			}
			g_list.add(g);
		    
			//System.out.println(lastThree());
			System.out.println(g);
			if (g_list.size() >= 10){
				if(g < 6)
					//System.out.println(count+": NOT INJECTING (g < 6) "+g);
					log("0");
				
				else {
					
					findDGDDG(3);
					
					if(dg < -0.4)
						//System.out.println(count+": NOT INJECTING (dg < -0.4) "+dg+"|"+ddg);
						log("0");
					else {
						
						//d = 0.8*g + 0.2*dg + 0.5*ddg-ic(n-1)
						//ic(n) = d + 0.9*ic(n-1)
						d = 0.8*g + 0.2*dg + 0.5*ddg-ic;
						d=Math.floor(d);
						ic = d + 0.9*ic;
						//log(Double.toString(d));
						//System.out.println(count+": INJECTING "+d+" (dg > -0.4) "+dg+"|"+ddg);
	//					bw.write(Double.toString(d));
						
					}
				}
			}
			
			else
				//log(count+"WAIT");
				log("WAIT");
			
		}
		bw.close();
		
	}
	
	
	
	private static String lastThree(){
		int size = g_list.size();
		if(size == 1)
			return "\nlast three: " + g_list.get(0);
		if(size == 2)
			return "\nlast three: " + g_list.get(0) +" -> "+ g_list.get(1);
		if(size > 2)
			return "\nlast three: " + g_list.get(size-3)  +" -> "+ g_list.get(size-2)  +" -> "+ g_list.get(size-1);
		return  null;
		
	}
	
	private static void log(String str){
		System.out.println(str);
		try {
			
			System.out.print("");
			//bw.write(str+"\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void findDGDDG(int num) {
		int size = g_list.size();
		ArrayList<Double> arr = new ArrayList<Double>();
		double sum = 0,x=-1,y=-1;
		
		for(int i=size-num; i<size-1; i++){
			arr.add( (g_list.get(i+1)-g_list.get(i)) );
			if (i == size-num)
				x = (g_list.get(i+1)-g_list.get(i));
			else
				y = (g_list.get(i+1)-g_list.get(i));
		}
		
		
		for(int i=0; i<arr.size(); i++)
			sum += arr.get(i);
		
		dg = sum/arr.size();
		ddg = (y - x)/2;
	}
		
			
	

}
