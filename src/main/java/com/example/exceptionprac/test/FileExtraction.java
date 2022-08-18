package com.example.exceptionprac.test;

import java.io.*;
import java.util.Arrays;

public class FileExtraction {

    static String baseDir = "/Users/zisoon/Desktop/FileExtraction"; // 검색할 폴더 경로
    static String word = "a,b,c,d"; // 검색할 단어
    static String save = "/Users/zisoon/Desktop/FileExtraction/result"; // 검색 결과가 저장될 파일 경로

    public static void main(String[] args) throws Exception {

        File dir = new File(baseDir);
        File[] files = dir.listFiles();
        Arrays.sort(files); // 파일 리스트 오름차순 정렬

        // 읽어들일 파일 input stream 선언
        BufferedReader br;
        String[] words = word.split(",");

        for ( int i = 1; i < files.length; i++ ) {

            File old = new File(save + "_" + i + ".txt");

            // 기존 파일과 지정한 파일명이 같으면 삭제
            if (files[i].equals(old)) {
                if (files[i].delete()) {
                    System.out.println("기존 파일 삭제 성공");
                } else {
                    System.out.println("기존 파일 삭제 실패");
                }
            } else {
                System.out.println("존재하는 파일이 없습니다.");
            }
        }

        // 파일 리스트 초기화
        files = dir.listFiles();

        // 해당 폴더 경로의 파일 리스트 갯수 만큼 아래의 구문 반복
        for ( int i = 1; i < files.length; i++ ) {

            // 파일이 아닌 경우, 아래의 로직 무시
            if(!files[i].isFile()) {
                continue;
            }

            // input stream 객체 생성
            br = new BufferedReader(new FileReader(files[i]));
            String line = "";

            // 저장할 파일의 output stream 생성
            PrintWriter writer = new PrintWriter(new FileWriter(save + "_" + i + ".txt"));

            StringBuilder sb = new StringBuilder();
            // 각 파일의 한 라인씩 읽어들이기
            while ( (line = br.readLine()) != null ) {
                for ( int j = 0; j < words.length; j++ ) {
                    // 검색하고자 하는 문자열이 라인에 포함되어 있으면 추가
                    if (line.contains(words[j])) {
                        sb.append(words[j]).append("\n");
                    }
                }
            }
            // 각 파일의 한 라인에 검색하고자 하는 문자열들을 String으로 형변환 후 결과 파일에 저장
            String str = sb.toString();
            writer.write(str.trim());

            writer.flush();

            br.close();

            writer.close();
        }
    }
}
