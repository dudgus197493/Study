package sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;


public class Back10989 {

	// 카운팅 정렬
	public static void main(String[] args) throws NumberFormatException, IOException {
//		solution1();
		solution2();
	}

	public static void solution1() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		// 입력 받기
		int N = Integer.parseInt(br.readLine());
		
		// 원본 배열
		int[] array = new int[N];
		
		// 배열 입력 받기
		for(int i =0; i<N; i++) {
			array[i] = Integer.parseInt(br.readLine());
		}
		
		// 최대값 구하기
		int maxVal = Arrays.stream(array).max().getAsInt();
		
		// 최댓값 구하기
//		int maxVal = array[0];
//		
//		for(int i =1; i<N; i++) {
//			if(maxVal < array[i]) {
//				maxVal = array[i];
//			}
//		}
		
		bw.write("최대값은 " + maxVal + "\n");
		
		// 최댓값 + 1의 크기를 가지는 카운팅배열 생성
		int[] count = new int[maxVal + 1];
		
		
//		for (int i =0; i<array.length; i++) {
//			count[array[i]]++;
//		}
		
		// 배열안의 수의 개수를 카운트하여 저장
		for(int i =0; i<count.length; i++) {
			for(int j = 0; j<N; j++) {
				if(array[j] == i) {
					count[i]++;
				}
			}
		}
		
//		for(int i =0; i<count.length; i++) {
//			for(int j = 0; j < N; j++) {
//				count[i] = Collections.frequency(Arrays.asList(array), i);
//			}
//		}
		
		for(int i = 0; i<count.length; i++) {
			bw.write(count[i] + " ");
		}
		bw.write("\n");
		// count배열을 누적합 배열로 변환
		for(int i = 1; i<count.length; i++) {
			count[i] = count[i - 1] + count[i];
		}
		// 출력
		for(int i = 0; i<count.length; i++) {
			bw.write(count[i] + " ");
		}
		bw.write("\n");
		
		// 정렬 결과를 담을 배열
		int[] result = new int[N];
		
		// 카운트 배열을 사용하여 정렬 진행
		for(int i = array.length - 1; i>=0; i--) {
//			result[count[array[i]]--] = array[i];
			int value = array[i];
			count[value]--;
			result[count[value]] = value;
		}
		
		for(int i = 0; i<result.length; i++) {
			bw.write(result[i] + " ");
		}
		
		br.close();
		bw.close();
	}
	
	public static void solution2() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		// 입력 받기
		int N = Integer.parseInt(br.readLine());
		
		// 카운팅 배열 생성 (수의 범위)
		int[] count = new int[10001];
		
		for(int i =0; i< N; i++) {
			count[Integer.parseInt(br.readLine())]++;
		}
		br.close();
		for(int i = 0; i < count.length; i++) {
			while(count[i] > 0) {
				sb.append(i).append("\n");
				count[i]--;
			}
		}
		bw.write(sb.toString());
		bw.close();
	}
}
