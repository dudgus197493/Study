package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HeapSort {
	
	public static void main(String[] args) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		StringTokenizer st = new StringTokenizer(br.readLine());
		
//		
//		Heap heap = new Heap(6);
//		heap.add(5);
//		heap.add(3);
//		heap.add(6);
//		
//		heap.remove();
//		
//		
//		System.out.println(heap);
		System.out.println("Hello World");
		
		int size = 5;
		int[] heap = {5, 2, 3, 6, 1};
		
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
		for(int i = size - 1; i >= 0; i--) {
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
		
		// 결과 출력
		for(int i =0; i< heap.length; i++) {
			System.out.printf("%d ", heap[i]);
		}
	}
}

class Heap {
	private int capacity;
	private int[] array;
	private int size;
	// 생성자
	public Heap(int capacity) {
		this.capacity = capacity;
		this.array = new int[capacity];
		this.size = 0;
	}
	
	// 부모 노드 위치 구하기
	public int getParentNode(int index) {
		return index / 2;
	}
	
	// 왼쪽 자식 노드 위치 구하기
	public int getLeftChildNode(int index) {
		return index * 2;
	}
	
	// 오른쪽 자식 노드 위치 구하기
	public int getRightChildNode(int index) {
		return index * 2 + 1;
	}
	
	// 데이터 추가
	public void add(int data) {
		// 배열의 가장 뒤에 삽입
		array[++this.size] = data;
		// 추가할 값의 인덱스
		int targetIndex = this.size;
		
		while(size > 1) {
			// 부모 노드 인데스
			int parentIndex = getParentNode(targetIndex);
			// 부모 노드의 값이 더 크거나 루트면
			if(this.array[parentIndex] > this.array[targetIndex] || targetIndex == 1) {
				return;
			}
			
			// 위치 교환
			array[this.size] = array[parentIndex];
			array[parentIndex] = data;
			targetIndex = parentIndex;
		}	
	}
	
	// 데이터 삭제
	public int remove() {
		int result = this.array[1];
		
		// 맨 뒤 노드를 루트 노드로 교환
		int targetIndex = 1;
		this.array[targetIndex] = this.array[this.size];
		this.array[this.size--] = 0;
		while(getLeftChildNode(targetIndex) < size) {
			// 왼쪽 자식 노드
			int leftChildNode = getLeftChildNode(targetIndex);
			// 오른쪽 자식 노드
			int rightChildNode = getRightChildNode(targetIndex);
			
			// 둘중 큰 값의 노드와 비교
			int childIndex = array[leftChildNode] > rightChildNode ? leftChildNode : rightChildNode;
			
			// 자리 교환
			int temp = array[childIndex];
			array[childIndex] = array[targetIndex];
			array[targetIndex] = temp;
			
			// 타겟인덱스 갱신
			targetIndex = childIndex;
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		for(int i = 1; i<this.array.length; i++) {
			result += array[i] + " ";
		}
		return result;
	}
}
