package controller;

import common.ReleaseStatus;
import dto.ReleaseDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import service.serviceImpl.ReleaseServiceImpl;

public class ReleaseController {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static ReleaseServiceImpl service = new ReleaseServiceImpl();
  static int id = 0;

  public static void main(String[] args){
      boolean loop = true;
      while(loop) {
          try {
              int role = 2;
              switch (role) {
                  case 1/*EMPLOYEE*/:
                      System.out.println("1. 출고 요청서 작성 2. 출고 조회 3. 운송장 조회 4. 나가기");
                      switch (Integer.parseInt(br.readLine())) {
                          case 1:
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
                              System.out.println("1. 전체 조회 2. 요청 승인 조회 3. 요청 거절 조회");
                              switch (Integer.parseInt(br.readLine())) {
                                  case 1:
                                      releaseAll();
                                      break;
                                  case 2:
                                      releaseApproval();
                                      break;
                                  case 3:
                                      releaseReject();
                                      break;
                              }
                          case 3:
                              break;
                          case 4:
                              loop = false;
                              break;
                          default:
                              System.out.println("다시 입력하세요.");
                              break;
                      }
                      break;
                  case 2/*ADMIN*/:
                  case 3/*SUPER_ADMIN*/:
                      System.out.println("1. 출고 관리 2. 배송 관리 3. 운송장 관리 4. 나가기");
                      switch (Integer.parseInt(br.readLine())) {
                          case 1:
                              System.out.println("1. 전체 조회 2. 요청 승인 조회 3. 요청 거절 조회 4. 출고 승인 5. 출고 거절 6. 출고 취소");
                              switch (Integer.parseInt(br.readLine())) {
                                  case 1:
                                      releaseAll();
                                      break;
                                  case 2:
                                      releaseApproval();
                                      break;
                                  case 3:
                                      releaseReject();
                                      break;
                                  case 4:
                                      System.out.println("출고 승인할 아이디 입력 (뒤로 가기 : 0) : ");
                                      id = Integer.parseInt(br.readLine());
                                      if (id == 0)
                                          break;
                                      int cal = service.stockCheck(id);
                                      if (cal >= 0) {
                                          System.out.printf("출고하면 창고에 %d개 남는다. 정말 할건가요?\n", cal);
                                          System.out.println("1. 예 2. 아니오");
                                          int select = Integer.parseInt(br.readLine());
                                          if (select == 1) {
                                              service.updateRelease(id, select);
                                              System.out.println("승인 완료");
                                          } else
                                              System.out.println("승인 실패");
                                      } else if (cal == -1)
                                          System.out.println("재고부족으로 출고 승인 불가");
                                      else
                                          System.out.println("출고가 이미 결정되었습니다.");
                                      break;
                                  case 5:
                                      System.out.println("출고 거절할 아이디 입력 (뒤로 가기 : 0) : ");
                                      id = Integer.parseInt(br.readLine());
                                      if (id == 0)
                                          break;
                                      else {
                                          service.updateRelease(id, 2);
                                          System.out.println("거절 완료");
                                      }
                                      break;
                                  case 6:
                                      System.out.println("출고 취소할 아이디 입력 (뒤로 가기 : 0) : ");
                                      id = Integer.parseInt(br.readLine());
                                      if (id == 0)
                                          break;
                                      else {
                                          service.updateRelease(id, 3);
                                          System.out.println("취소 완료");
                                      }
                                      break;
                                  default:
                                      System.out.println("다시 입력하세요.");
                                      break;
                              }
                              break;
                          case 2://배송
                              break;
                          case 3:
                              break;
                          case 4:
                              loop = false;
                              break;
                          default:
                              System.out.println("다시 입력하세요.");
                              break;
                      }
                      break;
                  default:
                      break;
              }
          }
          catch (IOException e){
              System.out.println("에러");
          }
          catch (NumberFormatException e){
              System.out.println("번호를 입력하세요.");
          }
      }
  }

  static void releaseReject() {
      List<ReleaseDto> releaseReject = service.getAll();
      boolean exist = false;
      System.out.println("출고 아이디 | 상품 코드 | 수령인 이름 | 수령인 주소 | 출고 개수 | 출고 상태 | 요청 사항 | 출고 요청 날짜 ㅣ 출고 거절 날짜");
      for (ReleaseDto dto : releaseReject) {
          if(dto.getReleaseStatus() == ReleaseStatus.REJECTED){
              exist = true;
              System.out.printf("\t\t%d \t|\t %s \t|\t %s \t|\t %s \t|\t %d \t|\t %s \t|\t %s \t|\t %s \t|\n",
                      dto.getId(),
                      dto.getProduct_id(),
                      dto.getCustomer_name(),
                      dto.getCustomer_address(),
                      dto.getAmount(),
                      dto.getReleaseStatus().toString(),
                      dto.getRemarks(),
                      dto.getUpdate_date());
          }
      }
      if(!exist)
          System.out.println("거절 리스트가 비어있습니다.");
  }

  static void releaseApproval() {
    List<ReleaseDto> releaseApproval = service.getAll();
    System.out.println("출고 아이디 | 상품 코드 | 수령인 이름 | 수령인 주소 | 출고 개수 | 출고 상태 | 요청 사항 | 출고 요청 날짜 | 출고 승인 날짜");

    boolean exist = false;
    for (ReleaseDto dto : releaseApproval) {
        if(dto.getReleaseStatus() == ReleaseStatus.APPROVED){
            exist = true;
            System.out.printf("\t\t%d \t|\t %s \t|\t %s \t|\t %s \t|\t %d \t|%s|\t %s \t|\t %s \t|\t %s \t|\n",
                    dto.getId(),
                    dto.getProduct_id(),
                    dto.getCustomer_name(),
                    dto.getCustomer_address(),
                    dto.getAmount(),
                    dto.getReleaseStatus().toString(),
                    dto.getRemarks(),
                    dto.getRequest_date(),
                    dto.getUpdate_date());
        }
    }
      if(!exist)
          System.out.println("승인 리스트가 비어있습니다.");
  }

  static void releaseAll() {
      List<ReleaseDto> releaseAll = service.getAll();

      if(releaseAll.isEmpty())
          System.out.println("출고 리스트가 비어있습니다.");
      else {
          System.out.println("출고 아이디 | 상품 코드 | 수령인 이름 | 수령인 주소 | 출고 개수 | 출고 상태 | 요청 사항 | 출고 요청 날짜 | 출고 승인 날짜");
          for (ReleaseDto dto : releaseAll) {
              String Approval_date = Optional.ofNullable(dto.getUpdate_date()).orElse("");
              System.out.printf("\t%d \t|\t %s \t|\t %s \t|\t %s \t|\t %d \t|\t %s \t|\t %s \t|\t %s \t|\t %s \t|\n",
                      dto.getId(),
                      dto.getProduct_id(),
                      dto.getCustomer_name(),
                      dto.getCustomer_address(),
                      dto.getAmount(),
                      dto.getReleaseStatus().toString(),
                      dto.getRemarks(),
                      dto.getRequest_date(),
                      Approval_date);
          }
      }
  }
}




