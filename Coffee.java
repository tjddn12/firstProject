package ezen_coffee_hsw;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Coffee {

    // 싱글톤 생성
    private static Coffee instance;

    private Coffee() {
    };

    public static Coffee getInstance() {
        if (instance == null) {
            instance = new Coffee();
        }
        return instance;
    }

    // 메뉴 리스트로 저장
    ArrayList<String> coffeList; // 메뉴 리스트
    ArrayList<Integer> coffePrice; // 각 메뉴 가격

    Map<String, Integer> menu;

    // 커피 메뉴 보여주기
    public void getMenu() {
        menu = new LinkedHashMap<String, Integer>();
        coffeList = new ArrayList<>();
        coffePrice = new ArrayList<>();

        // 메뉴 입력
        coffeList.add("아메리카노");
        coffeList.add("카푸치노");
        coffeList.add("아이스 아메리카노");
        coffeList.add("카라멜 마끼아또");
        coffeList.add("카페라떼");
        coffeList.add("카페모카");
        // 가격 입력
        coffePrice.add(2000); // 아메리카노
        coffePrice.add(4500); // 카푸치노
        coffePrice.add(2500); // 아이스 아메리카노
        coffePrice.add(4000); // 카라멜 마끼아또
        coffePrice.add(3000); // 카페라떼
        coffePrice.add(3500); // 카페모카

        // 맵에 저장 - 키 : 메뉴이름 벨류 : 가격
        for (int i = 0; i < coffeList.size(); i++) {

            menu.put(coffeList.get(i), coffePrice.get(i));
        }
        DecimalFormat f = new DecimalFormat("0,000원");

        // 반복문으로 전체 메뉴 보여주기
        StringBuffer st = new StringBuffer();
        st.append("\n\n ")
                .append("+----------------------------------------------------+\n ")
                .append("|===================== 메뉴판 =======================| \n ")
                .append("|         Menu                         Price         | \n");
        System.out.print(st.toString());
        int s = 1;
        for (Entry<String, Integer> get : menu.entrySet()) {
            System.out.printf(" | [%d번] %-11s\t: %s             |\n", s, get.getKey(), f.format(get.getValue()));
            s++;
        }
        System.out.println(" +----------------------------------------------------+\n");

    }// getMenu 끝


    // 메뉴 확인용 메인메소드 
    // public static void main(String[] args) {
    //     Coffee coffee = Coffee.getInstance();
    //     coffee.getMenu();
    // }

}
