package ezen_coffee_hsw;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * CoffeeSerive
 */
public class CoffeeSerive {

    private boolean reOrder = false;

    private int orderNum = 1;

    // private static CoffeeSerive cS = new CoffeeSerive();

    public CoffeeSerive() {

        orderList = new LinkedHashMap<>();
    }

    // public static CoffeeSerive getInstance() {
    // return cS;
    // };

    // 커피 객체 준비
    Coffee coffee = Coffee.getInstance();

    // 손님
    Customer customer;
    // 지연쓰레드 준비
    Thread t = new Thread();

    // 주문받은 음료 이름과 수량 담을 map 선언
    Map<String, Integer> orderList;

    Scanner sc = new Scanner(System.in);

    public void start() {

        System.out.println("\n 어서오세요 Ezen커피샵 입니다 ");
        customer = new Customer(orderNum);
        coffee.getMenu(); // 메뉴판
        // 주문 메소드
        order();
        // 최종 주문 내역
        totalOrder(customer);
        try {
            System.out.println("기다려주시면 주문하신 음료가 나옵니다 ");
            t.sleep(5000);
            end();
        } catch (Exception e) {

        }

    }

    // 음료 결과
    public void end() {
        int s = 1;
        StringBuffer message = new StringBuffer();
        message.append("\n\n ")
                .append("+----------------------------------------------------+\n ")
                .append("|                                                    | \n ")
                .append("|           " + customer.getOrderName() + "고객님 주문하신 음료 나왔습니다         | " + "\n");
        System.out.print(message);
        for (Entry<String, Integer> order : customer.getCoffeOrder().entrySet()) {

            System.out.printf(" | [%d] %-13s\t: %2d잔  %7s       | \n", s, order.getKey(), order.getValue(), "");

            s++;
        }
        System.out.println(" |                                                    |");
        System.out.println(" +----------------------------------------------------+");

    }

    // 주문
    private void order() {
        System.out.println("\n취소를 원하시면 0번을 눌러주세요");
        end: while (true) { // 추가 주문할수도 있기 때문에 무한루프
            try {
                System.out.print("\n원하시는 음료의 번호를 선택해주세요 > ");
                String choice = sc.next();

                int choiceNum = Integer.parseInt(choice.substring(0, 1)); // 개수 확인
                if (choiceNum == 0) {
                    System.out.println("주문을 취소합니다 ");
                    System.exit(0);
                }

                sc.nextLine();

                String coffeName = coffee.coffeList.get(choiceNum - 1);
                System.out.println("선택하신 음료는 : " + coffeName + "입니다 몇 잔을 주문하시겠습니까?");
                int orderCount = sc.nextInt();
                sc.nextLine(); // 버퍼 삭제
                // 재주문 if문
                if (reOrder) {

                    for (String coff : orderList.keySet()) {
                        if (coff.equals(coffeName)) {
                            int addCount = orderList.get(coff).intValue() + orderCount;
                            orderList.replace(coffeName, addCount);
                            break;
                        } else {
                            orderList.put(coffeName, orderCount);
                            break;
                        }
                    }

                    // ConcurrentModificationException 오류 해결 -> 내부 반복문이 아닌 외부 반복문으로 수정
                    // for (Entry<String, Integer> e : orderList.entrySet()) {
                    // if (e.getKey().equals(coffeName)) {
                    // int addCount = e.getValue() + orderCount; // 기존 개수에 추가
                    // orderList.replace(coffeName, addCount);
                    // break;
                    // } else {
                    // orderList.put(coffeName, orderCount);

                    // }
                    // }

                } else {
                    orderList.put(coffeName, orderCount); // key : 커피 이름 v = 개수
                }
                // 추가 주문 메소드
                addOrder();

                customer.setCoffeOrder(orderList);
                break end;

            } catch (Exception e) {
                System.out.println("\n잘못된 선택입니다");
                // e.printStackTrace(); // 에러메시지 테스트 끝나면 주석
                continue;
            }

        }

    }

    // 추가주문 여부
    private void addOrder() {
        reOrder = false;
        System.out.println("\n주문을 계속하시겠습니까?");
        System.out.println("예(Y) / 아니요(N)");
        String yesOrNo = sc.next();
        sc.nextLine();

        // 추가 주문시 메뉴판 보여주고 리스트에 추가
        if (yesOrNo.equals("예") || yesOrNo.equalsIgnoreCase("y")) {
            coffee.getMenu();

            reOrder = true;
            order();
        } else if (yesOrNo.equals("아니요") || yesOrNo.equalsIgnoreCase("n")) {

            return;
        }

    }

    // 최종 주문 결과
    private void totalOrder(Customer customer) {
        int s = 1;
        int totalmoney = 0;
        int coffePrice = 0;
        DecimalFormat f = new DecimalFormat("###,000원");

        String name = customer.getOrderName() + "번 ";

        StringBuffer message = new StringBuffer();
        message.append("\n\n ")
                .append("+----------------------------------------------------+\n ")
                .append("|                                                    | \n ")
                .append("|             " + name + "고객님 의 주문 내역 입니다         | " + "\n");

        for (Entry<String, Integer> order : customer.getCoffeOrder().entrySet()) {
            coffePrice = coffee.menu.get(order.getKey()).intValue()
                    * customer.getCoffeOrder().get(order.getKey()).intValue();
            totalmoney += coffePrice;
            String pay = f.format(coffePrice);
            message.append(
                    String.format(" | [%d] %-13s\t: %2d잔  %7s      | \n", s, order.getKey(), order.getValue(), pay));

            s++;
        }
        message.append(" |                                                    |\n ")
                .append("+----------------------------------------------------+ \n")
                .append(" ============ 총 결제 금액은 " + f.format(totalmoney) + "입니다 ========== \n");
        System.out.println(message);
        payment(totalmoney);// 결제 메소드

        return;
    }

    // 결제 메소드
    private void payment(int totalmoney) {

        System.out.println("\n 결제를 도와드리겠습니다 카드를 넣어주세요 ");
        int payResult = customer.getMoney() - totalmoney;
        try {

            t.sleep(2500);
            System.out.println("결제중입니다 .....");
            t.sleep(3000);

        } catch (InterruptedException e) {

        }
        if (payResult <= 0) {
            System.out.println("잔액이 부족합니다 \n 확인후 다시 주문해주세요  ");

            System.exit(0); // 종료

        } else {
            int paycalc = customer.getMoney() - totalmoney; //
            customer.setMoney(paycalc);
            System.out.println("결제가 완료 되었습니다 \n  ");
            System.out.println("이용해주셔서 감사합니다 ");
            orderNum++; // 다음 고객 번호 증가

        }
        return;

    }

}