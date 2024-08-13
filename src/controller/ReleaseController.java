package controller;

import common.ReleaseStatus;
import dto.ReleaseDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import service.serviceImpl.ReleaseServiceImpl;

public class ReleaseController {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {

    ReleaseServiceImpl service = new ReleaseServiceImpl();
    ReleaseStatus releaseStatus;

    boolean loop = true;
    while(loop) {
      switch (Role) {
        case EMPLOYEE:
          System.out.println("1. 출고 요청서 작성 2. 출고 조회 3. 배차 조회 4. 운송장 조회 5. 나가기");
          switch (Integer.parseInt(br.readLine())) {
            case 1:
              System.out.println("출고 요청");

              System.out.printf("수령인 이름 : ");
              String customer_name = br.readLine();

              System.out.printf("수령인 주소 : ");
              String customer_address = br.readLine();

              System.out.printf("출고할 상품 코드 : ");
              String product_name = br.readLine();

              System.out.printf("출고 수량 : ");

              System.out.printf("요청 사항 : ");
              String remarks = br.readLine();

              // 추가로 admin 정보가 들어감
              ReleaseDto releaseDto = new ReleaseDto(customer_name, customer_address, amount, product_name, ReleaseStatus.PENDING);

              service.createRelease(releaseDto); //service로 전달
          }
      }
          //else
        case SUPER_ADMIN:

          while(loop)
          System.out.println("1. 출고 관리 2. 배송 관리 3. 운송장 관리 4. 나가기");

    }
  }
}
