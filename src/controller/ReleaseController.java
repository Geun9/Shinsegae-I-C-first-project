package controller;

import common.ReleaseStatus;
import dao.daoImpl.ReleaseImplDao;
import dto.ReleaseDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import service.serviceImpl.ReleaseServiceImpl;

public class ReleaseController {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {

    ReleaseServiceImpl service = new ReleaseServiceImpl();
    ReleaseImplDao dao = new ReleaseImplDao();

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
              String product_code = br.readLine();

              System.out.printf("출고 수량 : ");
              int amount = Integer.parseInt(br.readLine());

              System.out.printf("요청 사항 : ");
              String remarks = br.readLine();

              // 추가로 admin 정보가 들어감
              ReleaseDto releaseDto = new ReleaseDto(customer_name,
                      customer_address,
                      amount,
                      product_code,
                      ReleaseStatus.PENDING,
                      remarks);


              service.createRelease(releaseDto); //service로 전달
              break;
            case 2:
              System.out.println("출고 조회");
              System.out.println("1. 전체 조회 2. 요청 승인 조회 3. 요청 거절 조회");
              switch (Integer.parseInt(br.readLine())) {
                case 1:
                  List<ReleaseDto> list = dao.findAll();
                  System.out.println(list);
                case 2:
                case 3:
              }

            case 3:
          }

          //else
        case ADMIN:
        case SUPER_ADMIN:
          System.out.println("1. 출고 관리 2. 배송 관리 3. 운송장 관리 4. 나가기");
          switch (Integer.parseInt(br.readLine())) {
            case 1:
              System.out.println("1. 전체 조회 2. 출고 거절 조회 3. 출고 승인 조회");
              //list 출력 id값도 볼 수 있음
              List<ReleaseDto> list = dao.findAll();
              System.out.println(list);
              //id값으로 테이블 접근
              System.out.println("1. 출고 승인 2. 출고 거절 3. 나가기");
              int select = Integer.parseInt(br.readLine());
              System.out.println("출고 아이디 입력 : ");
              int releaseId = Integer.parseInt(br.readLine());
              //1번을 선택했을 때 메소드를 통해 수량을 비교 하고 만약 재고가 부족하면 cli에서 "재고부족으로 출고 승인 불가" 출력 예정
              //결국 승인이 되지 않으며 거절 버튼을 누를 수 밖에 없게 한다.
              //재고가 부족하지 않으면 "출고하면 ??개 남는다. 정말 할건가요?" 이런식으로 한번 더 묻고 총 관리자가 결정하게 한다.
              //현재는 로직만 구현



              if(check) {
                System.out.println("출고하면 cal개 남는다. 정말 할건가요?");
                System.out.println("1. 예 2. 아니오");
                dao.approvalRelease(id);
              }
              else
                System.out.println("재고부족으로 출고 승인 불가");
          }
          break;
        default:
          break;
      }
    }
  }
}

