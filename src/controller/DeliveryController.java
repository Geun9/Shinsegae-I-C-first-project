package controller;

import service.serviceImpl.DeliveryServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeliveryController {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static DeliveryServiceImpl service = new DeliveryServiceImpl();
    static int id = 0;

    public static void main(String[] args) {
        boolean loop = true;
        try {
            while (loop) {
                System.out.println("1. 배차 등록 2. 배차 수정 3. 배차 취소 4. 조회 5. 종료");
                switch (Integer.parseInt(br.readLine())) {
                    case 1:
                        System.out.println("출고 아이디 입력(0 : 나가기) :");
                        id = Integer.parseInt(br.readLine());
                        if(id == 0)
                            break;
                        System.out.println("배차를 등록하시겠습니까?");
                        System.out.println("1.네 2. 아니요");
                        if(Integer.parseInt(br.readLine()) == 1)
                            service.createDelivery(id);
                        break;
                }
            }
        }
        catch (IOException e) {
            System.out.println("에러");
        }
    }
}