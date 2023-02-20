package sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Back2751 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		// 배열의 크기 입력 받기
		int N = Integer.parseInt(br.readLine());
		
		// 배열 선언
		int[] heap = new int[N];
		
		// 배열 입력 받기
		for(int i =0; i<N; i++) {
			heap[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		// 최초 힙 만들기
		for(int i = 1; i < heap.length; i++) {
			
			// 자식 노드 인덱스
			int child = i;
			do {
				// 부모 노드 인덱스
				int parent = (child - 1) / 2;
				
				// 자식 노드가 더 크다면
				if(heap[parent] < heap[child]) {
					// 위치 교환
					int temp = heap[parent];
					heap[parent] = heap[child];
					heap[child] = temp;
				}
				child = parent;
			} while(child != 0);
		}
		
		// 크기를 줄여가며 하향 선별을 사용하여 정렬
		for(int i = N - 1; i >= 0; i--) {
			// 마지막 노드와 루트 노드의 위치 교환
			int temp = heap[0];
			heap[0] = heap[i];
			heap[i] = temp;
			int root = 0;
			int child = 1;
			
			do {
				// 왼쪽 자식 노드의 인덱스 구하기
				child = root * 2 + 1; // root 가 0 일 때 1부타 시작하기 위한 + 1
				// 왼쪽 자식 노드 보다 오른쪽이 크다면
				if(child < i - 1 && heap[child] < heap[child + 1]) {
					// 오른쪽 자식 노드로 갱신
					child++;
				}
				
				// 자식 노드가 루트노드보다 크다면
				if(child < i && heap[root] < heap[child]) {
					// 위치 교환
					temp = heap[root];
					heap[root] = heap[child];
					heap[child] = temp;
				}
				// 루트 노드 위치 갱신
				root = child;
			} while(child < i);
		}
		
		for(int i =0; i< N; i++) {
			bw.write(heap[i] + "\n");
		}
		bw.close();
	}
}
