package sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Back11650 {
	/* 좌표 정렬하기
	 * 
	 * 문제
	 * 2차원 평면 위의 점 N개가 주어진다. 
	 * 좌표를 x좌표가 증가하는 순으로, x좌표가 같으면 y좌표가 증가하는 순서로 정렬한 다음 출력하는 프로그램을 작성하시오.
	 * 
	 * 입력
	 * 첫째 줄에 점의 개수 N (1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N개의 줄에는 i번점의 위치 xi와 yi가 주어진다. 
	 * (-100,000 ≤ xi, yi ≤ 100,000) 좌표는 항상 정수이고, 위치가 같은 두 점은 없다. 
	 * 
	 * 출력
	 * 첫째 줄부터 N개의 줄에 점을 정렬한 결과를 출력한다.
	 * 
	 *  */

	private static int[][] sorted;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		// 입력 횟수
		int N = Integer.parseInt(br.readLine());
		
		// 좌표 저장할 2차원 배열 (x, y)
		int[][] array = new int[N][2];
		
		// 정렬한 결과를 저장할 2차원 배열
		sorted = new int[N][2];
		
		// 입력 받기
		for(int i =0; i<array.length; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			array[i][0] = Integer.parseInt(st.nextToken());
			array[i][1] = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		// 배열의 크기가 1보다 클 때
		if(array.length > 1) {
			// 정렬하기
			mergeSort(array, 0, array.length - 1);
		} else {
			sorted[0] = array[0];
		}
		// 결과 출력
		for(int i =0; i < sorted.length; i++) {
			for(int j = 0; j < 2; j++) {
				bw.write(sorted[i][j] + " ");
			}
			bw.newLine();
		}
		bw.close();
	}
	
	// 병합정렬 - 분할
	public static void mergeSort(int[][] array, int left, int right) {
		if(left == right) {
			return;
		}
		
		// 중간 인덱스 구하기
		int mid = (left + right) / 2;
		
		// 중간 인덱스를 기준으로 왼쪽 배열 나누기
		mergeSort(array, left, mid);
		
		// 중간 인덱스를 기준으로 오른쪽 배열 나누기
		mergeSort(array, mid + 1, right);
		
		// 정렬하기
		merge(array, left, mid, right);
	}

	// 병합정렬 - 정렬
	public static void merge(int[][] array, int left, int mid, int right) {
		// 왼쪽 배열 시작 인덱스
		int leftIdx = left;
		// 오른쪽 배열 시작 인덱스
		int rightIdx = mid + 1;
		// 정렬할 배열 시작 인덱스 
		int idx = left;
		
		// 한쪽 배열이 다 정렬될 때까지 반복
		while(leftIdx <= mid && rightIdx <= right) {
			
			// 왼쪽배열의 값이 더 작을 때
			if(array[leftIdx][0] < array[rightIdx][0]) {
				sorted[idx] = array[leftIdx];
				idx++;
				leftIdx++;
				
			} else if(array[rightIdx][0] < array[leftIdx][0]) {
			// 오른쪽 배열의 값이 더 작을 때
				sorted[idx] = array[rightIdx];
				idx++;
				rightIdx++;
			} else {
			// 두 값이 같을 때
				// y값이 큰 쪽을 정렬
				if(array[leftIdx][1] < array[rightIdx][1]) {
					sorted[idx] = array[leftIdx];
					idx++;
					leftIdx++;
				} else {
					sorted[idx] = array[rightIdx];
					idx++;
					rightIdx++;
				}
			}
		} // while end
		
		// 왼쪽 배열이 먼저 정렬되었으면
		if(leftIdx > mid) {
			while(rightIdx <= right) {
				sorted[idx] = array[rightIdx];
				rightIdx++;
				idx++;
			}
		} else {
		// 오른쪽 배열이 먼저 정렬되었으면
			while(leftIdx <= mid) {
				sorted[idx] = array[leftIdx];
				leftIdx++;
				idx++;
			}
		}
		
		// 정렬된 배열 원본 배열에 복사
		for(int i =left; i<= right; i++) {
			array[i] = sorted[i];
		}
	}
}
