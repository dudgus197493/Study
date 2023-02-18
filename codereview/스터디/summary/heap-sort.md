# 힙 정렬(heap sort)
> ## 시간복잡도 : `O(NlogN)`

## 구현과정
> ### 힙의 성질인 루트 노드의 우선도는 최솟값 or 최댓값임을 이용
> ### 루트노드를 삭제하여 맨 뒤에 넣어주고 `깨진 힙을 재구조화`를 반복

![힙 정렬 구현 과정1](https://velog.velcdn.com/images%2Femplam27%2Fpost%2F5d2c2b06-676c-45dd-b7cd-c20c22134345%2F%ED%9E%99%EC%A0%95%EB%A0%AC1.png)
![힙 정렬 구현 과정1](https://velog.velcdn.com/images%2Femplam27%2Fpost%2F38089014-6d91-4285-80bf-bd524270a4f3%2F%ED%9E%99%EC%A0%95%EB%A0%AC2.png)

<br>

## Java 코드
```Java
public void heapSort() {
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
```