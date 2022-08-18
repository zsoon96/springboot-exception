package com.example.exceptionprac.error;

import java.util.Scanner;

public class ExceptionPrac {
    public static void main(String[] args) {
        try {
            // 오류가 발생할 것으로 예측되는 코드 입력
            int i = 0;

            // 사용자가 입력한 값
            System.out.println("입력해주세요.");
            Scanner sc = new Scanner(System.in);
            i = sc.nextInt();

            int [] num = {1,2,3,4,5};
            System.out.println("요청에 대한 응답 값 = " + num[i]);

            // 오류 생성
            // throw new Exception("사용자의 오류 생성");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // 특정 오류 처리
            System.out.println("요청한 값이 범위를 넘었습니다. 다시 입력해주세요.");
            System.out.println("오류 내용 = " + e.getMessage());
        }
        catch (Exception e) {
            // 특정 오류 외 일괄 처리
            System.out.println("오류가 발생했습니다.");
            System.out.println("오류 내용 = " + e.getMessage());
        }
        finally {
            // 무조건 실행되는 코드
            System.out.println("예외 처리 종료");
        }
    }

}
