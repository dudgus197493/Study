package sort;
import java.util.Scanner;

public class Back2750 {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		
		int N = sc.nextInt();
		int[] arr = new int[N];
		
		for(int i =0; i<N; i++) {
			arr[i] = sc.nextInt();
		}
		
		for(int i =1; i<arr.length; i++) {
			for(int j = 0; j<arr.length - i; j++) {
				
				if(arr[j] > arr[j+1]) {					
					
					int tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;
				}
			}
		}
		for(int i =0; i<arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
}