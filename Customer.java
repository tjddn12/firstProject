package ezen_coffee_hsw;

import java.util.Map;

public class Customer {
    private int orderName; // 주문 번호 
    private Map<String, Integer> coffeOrder; // 주문한 커피 와 금액을 담을 map 선언
    private int money; // 잔액

    public Customer(int s) {
        orderName = s; // 주문 번호 변경 불가능 생성자로 통해 넣기
        // 초기 금액설정
        this.money = 300000; 

    }

    public int getOrderName() {
        return orderName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // public void setOrderName(int orderName) {
    //     this.orderName = orderName;
    // }

    public Map<String, Integer> getCoffeOrder() {
        return coffeOrder;
    }

    public void setCoffeOrder(Map<String, Integer> coffeOrder) {
        this.coffeOrder = coffeOrder;
    }

}
