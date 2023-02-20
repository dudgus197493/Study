package sort;

import java.util.Scanner;

public class Back2587 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		// 5개의 입력값 저장할 변수
		int[] inputArr = new int[5];
		
		// 평균값을 구하기 위한 변수
		int sum = 0;
		
		// 5개의 입력값 받기
		for(int i =0; i<5; i++) {
			int temp = sc.nextInt();
			inputArr[i] = temp;
			sum += temp;
		}
		
		// 배열 정렬
		for(int i = 1; i< inputArr.length; i++) {
			for(int j = 0; j<inputArr.length - i; j++) {
				if(inputArr[j] > inputArr[j+1]) {
					int temp = inputArr[j];
					inputArr[j] = inputArr[j+1];
					inputArr[j+1] = temp;
				}
			}
		}
		
		// 결과 출력
		System.out.printf("%d\n%d", sum / 5, inputArr[2]);
	}
}
