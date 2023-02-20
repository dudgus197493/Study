package sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Back1931 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		
		int N = Integer.parseInt(br.readLine());
		
		int[] start = new int[N];
		int[] end = new int[N];
		
		for(int i =0; i< N; i++) {
			String[] temp = br.readLine().split(" ");
			start[i] = Integer.parseInt(temp[0]);
			end[i] = Integer.parseInt(temp[1]);
		}
		
		Arrays.sort(end);
		Arrays.sort(start);
		
		int currentEnd = 0;
		
		for(int i =0; i< N; i++) {
			for( int j =0; j< N; j++) {
				if(j != i) {
					
				}
			}
		}
	}

}
