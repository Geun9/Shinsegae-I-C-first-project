package controller;

import dto.StockDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import service.serviceImpl.StockServiceImpl;

public class StockController {

  private static final StockServiceImpl stockService = StockServiceImpl.getInstance();
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {
    StockController controller = new StockController();
    try {
      controller.run(); // 실행
    } catch (IOException e) {
      System.err.println("입출력 오류 발생: " + e.getMessage());
    }
  }

  private void run() throws IOException {
    // 사용자 입력을 받아 메뉴를 표시
    while (true) {
      System.out.println("Admin 1 || User 2 || Exit 3");
      int choice = readIntInput();
      if (choice == 3) {
        break;
      }
      handleUserChoice(choice);
    }
  }

  private void handleUserChoice(int userType) throws IOException {
    boolean continueLoop = true;
    while (continueLoop) {
      if (userType == 1) {
        // 관리자 메뉴
        continueLoop = displayAdminMenu(); // 메뉴에서 나가기 시 false 반환
      } else if (userType == 2) {
        // 사용자 메뉴
        continueLoop = displayUserMenu(); // 메뉴에서 나가기 시 false 반환
      } else {
        System.out.println("잘못된 선택입니다.");
        return; // 잘못된 선택 시 종료
      }
    }
  }

  private boolean displayAdminMenu() throws IOException {
    // 관리자 메뉴
    System.out.println("재고 관리 메뉴");
    System.out.println("1. 재고 조회 || 2. 재고 조회 나가기");
    int choice = readIntInput();

    switch (choice) {
      case 1:
        viewStockAsAdmin(); // 재고 조회
        return true; // 계속 메뉴를 표시
      case 2:
        return false; // 메뉴를 나가서 상위 메뉴로 돌아감
      default:
        System.out.println("잘못된 선택입니다.");
        return true; // 잘못된 선택 시 계속 메뉴를 표시
    }
  }

  private boolean displayUserMenu() throws IOException {
    // 사용자 메뉴
    System.out.println("재고 관리 메뉴");
    System.out.println("1. 재고 조회 || 2. 재고 조회 나가기");
    int choice = readIntInput();

    switch (choice) {
      case 1:
        viewStockAsUser(1); // 사용자 ID가 1인 재고 조회
        return true; // 계속 메뉴를 표시
      case 2:
        return false; // 메뉴를 나가서 상위 메뉴로 돌아감
      default:
        System.out.println("잘못된 선택입니다.");
        return true; // 잘못된 선택 시 계속 메뉴를 표시
    }
  }

  private void viewStockAsAdmin() throws IOException {
    // 관리자 재고 조회
    System.out.println("1. 전체 조회 || 2. 대분류 조회");
    int choice = readIntInput();

    switch (choice) {
      case 1:
        displayStockList(stockService.getAllStock()); // 전체 재고 조회
        break;
      case 2:
        // 대분류 필터링
        if (!filterStockByCategory()) {
          return; // 입력이 잘못되면 재고 관리 메뉴로 돌아감
        }
        break;
      default:
        System.out.println("잘못된 선택입니다.");
    }
  }

  private boolean filterStockByCategory() throws IOException {
    // 대분류, 중분류, 소분류에 따른 재고 조회
    System.out.println("조회할 대분류 입력하세요: ");
    String category = br.readLine();
    if (category.isEmpty() || stockService.getFilteredStock(category, 1).isEmpty()) {
      System.out.println("잘못된 대분류 입력입니다. 재고 관리 메뉴로 돌아갑니다.");
      return false;
    }
    displayStockList(stockService.getFilteredStock(category, 1)); // 대분류 재고 조회

    // 중분류 조회
    System.out.println("중분류: 1. 조회하기 || 2. 조회 나가기");
    int choice = readIntInput();
    if (choice == 1) {
      System.out.println("조회할 중분류 입력하세요: ");
      category = br.readLine();
      if (category.isEmpty() || stockService.getFilteredStock(category, 2).isEmpty()) {
        System.out.println("잘못된 중분류 입력입니다. 재고 관리 메뉴로 돌아갑니다.");
        return false;
      }
      displayStockList(stockService.getFilteredStock(category, 2)); // 중분류 재고 조회


      // 소분류 조회
      System.out.println("소분류: 1. 조회하기 || 2. 조회 나가기");
      choice = readIntInput();
      if (choice == 1) {
        System.out.println("소분류를 입력해주세요: ");
        category = br.readLine();
        if (category.isEmpty() || stockService.getFilteredStock(category, 3).isEmpty()) {
          System.out.println("잘못된 소분류 입력입니다. 재고 관리 메뉴로 돌아갑니다.");
          return false;
        }
        displayStockList(stockService.getFilteredStock(category, 3)); // 소분류 재고 조회
      }
    }
    return true;
  }

  private void viewStockAsUser(int userId) {
    // 사용자 재고 조회
    displayStockList(stockService.getUserStock(userId));
  }

  private void displayStockList(List<StockDto> stockDtos) {
    // 포맷 문자열 정의
    final String headerFormat = "%-15s %-15s %-20s %-20s %-20s %-20s %-15s %-20s%n";
    final String rowFormat = "%-15s %-15s %-20s %-20s %-13d %-24s %-23s %-13d%n";

    // 헤더 출력
    System.out.printf(headerFormat,
        "대분류", "중분류", "소분류", "제품 이름", "창고 ID", "창고 이름", "창고 주소", "제품 재고량");

    // 데이터 출력
    for (StockDto stockDto : stockDtos) {
      System.out.printf(rowFormat,
          stockDto.getT_category(),
          stockDto.getI_category(),
          stockDto.getS_category(),
          stockDto.getP_name(),
          stockDto.getWarehouse_id(),
          stockDto.getWarehouse_name(),
          stockDto.getAddress_city(),
          stockDto.getQuantity());
    }
  }

  private int readIntInput() {
    while (true) {
      try {
        return Integer.parseInt(br.readLine());
      } catch (NumberFormatException | IOException e) {
        System.out.println("숫자를 입력하세요.");
      }
    }
  }
}
