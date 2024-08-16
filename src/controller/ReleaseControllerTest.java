package controller;

import common.ReleaseStatus;
import dto.ReleaseDto;
import service.serviceImpl.ReleaseServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ReleaseControllerTest {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static ReleaseServiceImpl service = new ReleaseServiceImpl();

  public static void main(String[] args) throws IOException {
    //롤백 고려(ex 출고 요청 취소)
    boolean loop = true;
    while (loop) {
      System.out.println("1. 출고 요청서 작성 2. 출고 조회 3. 운송장 조회 4. 나가기");
      switch (Integer.parseInt(br.readLine())) {
        case 1:
          System.out.println("출고 요청");

          System.out.printf("수령인 이름 : ");
          String customer_name = br.readLine();

          System.out.printf("수령인 주소 : ");
          String customer_address = br.readLine();

          System.out.printf("출고할 상품 코드 : ");
          String product_id = br.readLine();

          System.out.printf("출고 수량 : ");
          int amount = Integer.parseInt(br.readLine());

          System.out.printf("요청 사항 : ");
          String remarks = br.readLine();

          // 추가로 admin 정보가 들어감
          ReleaseDto releaseDto = new ReleaseDto(
                  customer_name,
                  customer_address,
                  amount,
                  product_id,
                  ReleaseStatus.PENDING,
                  remarks);

          service.createRelease(releaseDto); //service로 전달
          break;
        case 2:
          System.out.println("출고 조회");
          //조회 추가
          System.out.println("1. 전체 조회 2. 요청 승인 조회 3. 요청 거절 조회");
          switch (Integer.parseInt(br.readLine())) {
            case 1:
              List<ReleaseDto> releaseAll = service.getAll();
              for (ReleaseDto dto : releaseAll) {
                System.out.println("출고 아이디 | 상품 코드 | 수령인 이름 | 수령인 주소 | 출고 개수 | 출고 상태 | 요청 사항");
                System.out.printf("\t\t%d \t|\t %s \t|\t %s \t|\t %s \t|\t %d \t|\t %s \t|\t %s \t|\n", dto.getId(),
                        dto.getProduct_id(),
                        dto.getCustomer_name(),
                        dto.getCustomer_address(),
                        dto.getAmount(),
                        dto.getReleaseStatus().name(),
                        dto.getRemarks());
              }
            case 2:
              List<ReleaseDto> releaseApproval = service.getAll();
              System.out.println("출고 아이디 | 상품 코드 | 수령인 이름 | 수령인 주소 | 출고 개수 | 출고 상태 | 요청 사항");
              for (ReleaseDto dto : releaseApproval) {
                if(dto.getReleaseStatus().name().equals(ReleaseStatus.APPROVED)){
                  System.out.printf("\t\t%d \t|\t %s \t|\t %s \t|\t %s \t|\t %d \t|\t %s \t|\t %s \t|\n", dto.getId(),
                          dto.getProduct_id(),
                          dto.getCustomer_name(),
                          dto.getCustomer_address(),
                          dto.getAmount(),
                          dto.getReleaseStatus().name(),
                          dto.getRemarks());
                }
              }
            case 3:
              List<ReleaseDto> releaseReject = service.getAll();
              System.out.println("출고 아이디 | 상품 코드 | 수령인 이름 | 수령인 주소 | 출고 개수 | 출고 상태 | 요청 사항");
              for (ReleaseDto dto : releaseReject) {
                if(dto.getReleaseStatus().name().equals(ReleaseStatus.REJECTED)){
                  System.out.printf("\t\t%d \t|\t %s \t|\t %s \t|\t %s \t|\t %d \t|\t %s \t|\t %s \t|\n", dto.getId(),
                          dto.getProduct_id(),
                          dto.getCustomer_name(),
                          dto.getCustomer_address(),
                          dto.getAmount(),
                          dto.getReleaseStatus().name(),
                          dto.getRemarks());
                }
              }
          }
        case 3:
      }
    }
  }
}
