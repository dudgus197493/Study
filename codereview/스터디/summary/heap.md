# 힙(heap)
> ### 완전 이진트리의 일종
> ### 우선순위 큐를 위하여 만들어진 자료구조
> ### 최댓값, 최솟값을 쉽게 추출할 수 있는 자료구조

<br>

## 힙의 사용 이유
예를 들어 n개의 데이터가 저장된 배열에서 최댓값 혹은 최솟값을 구하려고 할 때
데이터가 추가, 삭제 될 때 마다 오름차순/내림차순으로 정렬을 해야한다.

<br>

## 힙의 조건
- 부모 노드는 자식 노드보다 항상 우선순위가 높다.
- 부모 노드는 자식 노드보다 항상 우선순위가 앞선다는 조건을 유지하며 완전이진트리 형태로 채워나간다.
- 위 규칙을 지킬 때 `루트 노드`는 항상 우선순위가 높은 노드다.
- 형재간의 우선순위는 고려되지 않는다.(반 정렬 상태, 느슨한 정렬 상태, 약한 힙(weak heap))
![힙의 조건](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcUJahp%2FbtqV36KtdQ4%2FLzdICgKMk7KvYULPjkKQy1%2Fimg.png)

<br>

## 시간복잡도
- 최댓값, 최솟값을 빠르게 찾을 수 있다.(시간복잡도 : `O(1)`)
- 삽입, 삭제 연산시 부모노드가 자식노드보다 우선순위만 높으면 되므로 트리의 깊이만큼 비교 진행(시간복잡도 : `O(logN)`)

<br>

## 힙의 종류
> ### 최소 힙 : 부모 노드의 값(key 값) ≤ 자식 노드의 값(key 값)  
> ### 최대 힙 : 부모 노드의 값(key 값) ≥ 자식 노드의 값(key 값)
![힙의종류](https://img1.daumcdn.net/thumb/R1920x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbXeFO2%2FbtqVTGz4Spk%2FEmiJ4rN545GnSjLddKZnT0%2Fimg.png)

<br>

## 힙의 구현
> ### 기본적으로 배열을 사용해 구현 (연결리스트로도 가능하나 특정 노드의 '검색', '이동'에서 비용발생)

![힙의 구현](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FWDMjZ%2FbtqV8GEMhcb%2FM2Wm02OJQhSh7sdW1kSzLK%2Fimg.png)

- ### 특징
    - 구현의 용이함을 위해 시작 인덱스는(root) 1부터 시작
    - 각 노드와 대응되는 배열의 인덱스는 '불변'

- ### 성질
    - `왼쪽 자식 노드 인덱스` = 부모 노드 인덱스 x 2
    - `오른쪽 자식 노드 인덱스` = 부모 노드 인덱스 x 2 + 1
    - `부모 노드 인덱스` = 자식 노드 인덱스 / 2
```Java
public class Heap {
    private int size;      // 힙의 현재 사이즈
    private int capacity;  // 힙의 용량
    private int[] array;   // 데이터를 담을 배열

    // 생성자
    public Heap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.array = new int[this.capacity];
    }

    // 부모 노드 위치 구하기
    public int getParentNode(int idx) {
        return idx / 2;
    }

    // 왼쪽 자식 노드 위치 구하기
    public int getLeftChildNode(int idx) {
        return idx * 2;
    }

    // 오른쪽 자식 노드 위치 구하기
    public int getRightChildNode(int idx) {
        return idx * 2 + 1;
    }
}
```



<br>





## 힙의 상향 선별(sift-up)
> ### 힙의 데이터 추가 혹은 정렬을 위해 부모노드를 찾아가면서 힙의 조건이 맞을 때 까지 요소를 교환해가며 올라가는 방식

![상향 선별](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fci59jf%2FbtqWNbRsmLA%2FScwEtG3n9neieCEJBVeos0%2Fimg.png)
```Java
// 데이터 추가 (상향 선별)
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
```

<br>

## 힙의 하향 선별(sift-down)
> ### 힙의 데이터를 삭제 혹은 정렬을 위해 루트노드가 자식노드와 비교하며 힙의 조건이 맞지 않거나 자식노드가 없을 때 까지 교환하며 내려가는 방식

![하향 선별](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb34svU%2FbtqWx5ZYjtI%2FKaMAkGXcQOh5Qe1HIKTpPk%2Fimg.png)
```Java
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
```