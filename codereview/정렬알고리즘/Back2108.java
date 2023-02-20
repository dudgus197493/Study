package sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Back2108 {

//	산술평균 : N개의 수들의 합을 N으로 나눈 값
//	중앙값 : N개의 수들을 증가하는 순서로 나열했을 경우 그 중앙에 위치하는 값
//	최빈값 : N개의 수들 중 가장 많이 나타나는 값
//	범위 : N개의 수들 중 최댓값과 최솟값의 차이

// N개의 수가 주어졌을 때, 네 가지 기본 통계값을 구하는 프로그램을 작성하시오.
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		solution2();
	}

	public static void solution1() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		// 개수 입력 받기
		int N = Integer.parseInt(br.readLine());
		
		// 산술평균을 구하기 위한 합을 담을 변수
		double sum = 0;
		
		// 입력 받을 배열
		int[] array = new int[N];
		
		// 최빈값을 구하기 위한 맵
		Map<Integer, Integer> map = new HashMap<>();
		
		// 최빈값을 구하기 위한 배열
		List<Integer> tempList = new ArrayList<>();
		
		// 최대 중복 횟수 저장할 변수 선언
		int maxCount = 1;
		
		// 입력 받기 + 최대 힙 유지 하기
		for(int i =0; i<N; i++) {
			int val = Integer.parseInt(br.readLine());
			
			if(map.get(val) == null) {
				map.put(val, 1);
			} else {
				int count = map.get(val) + 1;
				map.put(val, count);
				if(maxCount < count) {
					maxCount = count;
				}
			}
			
			sum += val;
			array[i] = val;
			int child = i;
			
			// 현재 자식 노드가 루트노드가 아니면
			while(child != 0) {
				// 부모 노드 인덱스 구하기
				int parent = child / 2;
				
				// 자식 노드가 부모 노드보다 크면
				if(array[child] > array[parent]) {
					// 위치 교환
					int temp = array[child];
					array[child] = array[parent];
					array[parent] = temp;
				}
				child = parent;
			}
		}
		br.close();
		
		// 힙 정렬
		for(int i =array.length - 1; i >= 0; i--) {
			// 마지막 노드와 루트 노드의 위치 교환
			int temp = array[0];
			array[0] = array[i];
			array[i] = temp;
			int root = 0;
			int child = 1;
			
			do {
				// 왼쪽 자식 노드의 인덱스 구하기
				// root 가 0 일 때 1부타 시작하기 위한 + 1
				child = root * 2 + 1;
				
				// 왼쪽 자식 노드 보다 오른쪽이 크다면
				if(child < i - 1 && array[child] < array[child + 1]) {
					// 오른쪽 자식 노드로 갱신
					child++;
				}
				// 자식 노드가 루트노드보다 크다면
				if(child < i && array[child] > array[root]) {
					// 위치 교환
					temp = array[child];
					array[child] = array[root];
					array[root] = temp;
				}
				// 루트 노드 위치 갱신		
				root = child;
			} while(child < i);			
		}
		
		int mode = 0;
		
		if(maxCount > 1) {
			for(Integer Key : map.keySet()) {
				if(map.get(Key) == maxCount) {
					System.out.println("key값 + " + Key);
					tempList.add(Key);
				}
			}
			if(tempList.size() > 1) {
				Collections.sort(tempList);
				mode = tempList.get(1);				
			} else {
				mode = tempList.get(0);
			}
		} else {
			mode = array.length > 1 ? array[1] : array[0];
		}
		
		for(int i =0; i<array.length; i++) {
			bw.write(array[i] + " ");
		}
		System.out.println(maxCount);
		System.out.println(tempList);
		bw.write("\n");
		
		bw.write("답:\n");
		bw.write(Math.round(sum / N)+ "\n" + array[N/2] + "\n" + mode + "\n" + (array[N-1] - array[0]));

		bw.close();
	}
	public static void solution2() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		
		// 개수 입력 받기
		int N = Integer.parseInt(br.readLine());
		
		// 산술평균을 구하기 위한 합을 담을 변수
		double sum = 0;
		
		// 입력 받을 배열
		int[] array = new int[N];
		
		// 최빈값을 구하기 위한 맵
		Map<Integer, Integer> map = new HashMap<>();
		
		// 최빈값을 구하기 위한 배열
		List<Integer> tempList = new ArrayList<>();
		
		// 최대 중복 횟수 저장할 변수 선언
		int maxCount = 1;
		
		// 입력 받기 + 최대 힙 유지 하기
		for(int i =0; i<N; i++) {
			int val = Integer.parseInt(br.readLine());
			
			if(map.get(val) == null) {
				map.put(val, 1);
			} else {
				int count = map.get(val) + 1;
				map.put(val, count);
				if(maxCount < count) {
					maxCount = count;
				}
			}
			
			sum += val;
			array[i] = val;
			int child = i;
			
			// 현재 자식 노드가 루트노드가 아니면
			while(child != 0) {
				// 부모 노드 인덱스 구하기
				int parent = child / 2;
				
				// 자식 노드가 부모 노드보다 크면
				if(array[child] > array[parent]) {
					// 위치 교환
					int temp = array[child];
					array[child] = array[parent];
					array[parent] = temp;
				}
				child = parent;
			}
		}
		br.close();
		
		// 힙 정렬
		for(int i =array.length - 1; i >= 0; i--) {
			// 마지막 노드와 루트 노드의 위치 교환
			int temp = array[0];
			array[0] = array[i];
			array[i] = temp;
			int root = 0;
			int child = 1;
			
			do {
				// 왼쪽 자식 노드의 인덱스 구하기
				// root 가 0 일 때 1부타 시작하기 위한 + 1
				child = root * 2 + 1;
				
				// 왼쪽 자식 노드 보다 오른쪽이 크다면
				if(child < i - 1 && array[child] < array[child + 1]) {
					// 오른쪽 자식 노드로 갱신
					child++;
				}
				// 자식 노드가 루트노드보다 크다면
				if(child < i && array[child] > array[root]) {
					// 위치 교환
					temp = array[child];
					array[child] = array[root];
					array[root] = temp;
				}
				// 루트 노드 위치 갱신		
				root = child;
			} while(child < i);			
		}
		
		int mode = 0;
		
		if(maxCount > 1) {
			for(Integer Key : map.keySet()) {
				if(map.get(Key) == maxCount) {
					tempList.add(Key);
				}
			}
			if(tempList.size() > 1) {
				Collections.sort(tempList);
				mode = tempList.get(1);				
			} else {
				mode = tempList.get(0);
			}
		} else {
			mode = array.length > 1 ? array[1] : array[0];
		}
		
		bw.write(Math.round(sum / N)+ "\n");
		bw.write(array[N/2] + "\n");
		bw.write(mode + "\n");
		bw.write((array[N-1] - array[0]) + "");

		bw.close();
	}
	
	// 카운트 정렬
	public static void solution3() throws NumberFormatException, IOException {
		BufferedWriter 
	}
	
}
