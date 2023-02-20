package sort;

import java.util.Scanner;

public class Back25305 {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		// 사람 수
		int N = sc.nextInt();
		
		// 커트라인 기준
		int cutLine = sc.nextInt();
		
		// 점수 입력 받을 배열
		int[] score = new int[N];
		
		// 점수 입력 받기
		for(int i =0; i<score.length; i++) {
			score[i] = sc.nextInt();
		}
		
		// 오름차순 정렬
		boolean flag = true;
		for(int i =1; i< N; i++) {
			for(int j = 0; j< N - i; j++) {
				if(score[j] < score[j+1]) {
					int tmp = score[j];
					score[j] = score[j+1];
					score[j+1] = tmp;
					flag = false;
				}
			}
			
			if(flag) {
				break;
			}
		}
		
		// 컷트라인의 점수 출력
		System.out.println(score[cutLine - 1]);
	}
}