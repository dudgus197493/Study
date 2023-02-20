package sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Back1427 {

	/* 문제 
	 * 배열을 정렬하는 것은 쉽다. 수가 주어지면, 그 수의 각 자리수를 내림차순으로 정렬해보자.
	 * 
	 * 입력
	 * 첫째 줄에 정렬하려고 하는 수 N이 주어진다. N은 1,000,000,000보다 작거나 같은 자연수이다.
	 * 
	 * 출력
	 * 첫째 줄에 자리수를 내림차순으로 정렬한 수를 출력한다.
	 * */
	private static int[] sorted;
	
	public static void main(String[] args) throws Exception {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		br.close();
		
		sorted = new int[input.length()];
		
		// 입력값이 2자릿수 이상일 때
		if(input.length() > 1) {
			int[] array = new int[input.length()];
			
			for(int i =0; i<input.length(); i++) {
				array[i] = Integer.parseInt(input.valueOf(input.charAt(i)));
			}
			
			mergeSort(array, 0, array.length-1);
			
		// 1자릿수면	
		} else {
			sorted[0] = Integer.parseInt(input);
		}
		
		// 결과 출력
		for(int i =0; i< sorted.length; i++) {
			bw.write(sorted[i]+"");
		}
		bw.close();
	}

	public static void mergeSort(int[] array, int left, int right) throws IOException {
		
		// 원소가 한개밖에 없으면
		if(left == right) {
			return;
		}
		
		// 중간 인덱스
		int mid = (left + right) / 2;
		
		// 중간을 기준으로 왼쪽 배열 나누기
		mergeSort(array, left, mid);
		
		// 중간을 기준으로 오른쪽 배열 나누기
		mergeSort(array, mid + 1, right);
		
		// 합치기
		merge(array, left, mid, right);
	}
	
	// 배열 합치기
	public static void merge(int[] array, int left, int mid, int right) throws IOException {
		
		// 왼쪽 배열 시작 인덱스
		int leftIdx = left;
		// 오른쪽 배열 시작 인덱스
		int rightIdx = mid + 1;
		// 채워넣을 시작 인덱스
		int idx = left;
		
		
		while(leftIdx <= mid && rightIdx <= right) {
			// 두 개의 배열 중 왼쪽배열의 값이 더 클 때
			if(array[leftIdx] >= array[rightIdx]) {
				// 정렬할 배열에 값 삽입
				sorted[idx] = array[leftIdx];
				// 왼쪽배열의 커서 한칸 이동
				leftIdx++;
				// 채워넣을 인덱스 커서 한칸 이동
				idx++;
				
			// 두 개의 배열 중 오른쪽배열의 값이 더 클 때
			} else {
				// 정렬할 배열에 값 삽입
				sorted[idx] = array[rightIdx];
				// 왼쪽배열의 커서 한칸 이동
				rightIdx++;
				// 채워넣을 인덱스 커서 한칸 이동
				idx++;
			}			
		} // while end
		
		// 만약 왼쪽 배열이 먼저 채워졌을 때
		if(leftIdx > mid) {
			// 오른쪽 배열을 다 채울 때 까지
			while(rightIdx <= right) {
				// 정렬할 배열에 값 삽입
				sorted[idx] = array[rightIdx];
				// 오른쪽 배열의 커서 한칸 이동
				rightIdx++;
				// 채워넣을 인덱스 커서 한칸 이동
				idx++;
			}
		// 만약 오른쪽 배열이 먼저 채워졌을 때
		} else {
			// 왼쪽 배열을 다 채울 때 까지
			while(leftIdx <= mid) {
				// 정렬할 배열에 값 삽입
				sorted[idx] = array[leftIdx];
				// 왼쪽 배열의 커서 한칸 이동
				leftIdx++;
				// 채워넣을 인덱스 커서 한칸 이동
				idx++;
			}
		}
		
		// 원본 배열에 정렬된 배열 복사
		for(int i =left; i<= right; i++) {
			array[i] = sorted[i];
		}
	}
}
