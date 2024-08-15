package controller;


import dto.StockDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import service.serviceImpl.StockServiceImpl;

public class StockController {

  private final StockServiceImpl stockService = new StockServiceImpl();
  private final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    StockController controller = new StockController();
    controller.displayMenu();
  }
  /*public static void main(String[] args) {
    StockController stockController = new StockController();
    stockController.menu();
  }*/

  /*public void menu() {
    int userType = 1; // 관리자
    try {
      if (userType == 1) {
        System.out.println("재고 관리 메뉴");
        System.out.println("1. 재고 조회 | 2. 나가기");
        int check = Integer.parseInt(br.readLine());
        switch (check) {
          case 1 -> {}
          case 2 -> { break; }
        }
      }
    } catch (Exception e){
      System.out.println("오류 발생 메뉴로 돌아갑니다.");
      menu();
    }
  }*/

  public void displayMenu() throws IOException {
    while (true) {
      System.out.println("1. 재고 조회");
      System.out.println("2. 재고 추가");
      System.out.println("3. 재고 업데이트");
      System.out.println("4. 재고 삭제");
      System.out.println("5. 대분류별 재고 조회");
      System.out.println("6. 중분류별 재고 조회");
      System.out.println("7. 소분류별 재고 조회");
      System.out.println("0. 종료");
      System.out.print("선택: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          listAllStocks();
          break;
        case 2:
          addStock();
          break;
        case 3:
          updateStock();
          break;
        case 4:
          deleteStock();
          break;
        case 5: // 대분류
        case 6: // 중분류
        case 7: // 소분류
          break;
        case 0:
          System.out.println("종료합니다.");
          return;
        default:
          System.out.println("잘못된 선택입니다.");
      }
    }
  }

  private void listAllStocks() {
    List<StockDto> stocks = stockService.getAllStocks();
    for (StockDto stock : stocks) {
      System.out.println("ID: " + stock.getId() +
          ", 상품 ID: " + stock.getProduct_id() +
          ", 창고 ID: " + stock.getWarehouse_id() +
          ", 재고 수량: " + stock.getQuantity() +
          ", 상태: " + stock.getStatus() +
          ", 등록일: " + stock.getCreated_at() +
          ", 수정일: " + stock.getUpdated_at());
    }
  }

  private void addStock() {
    System.out.print("상품 ID: ");
    String productId = scanner.next();
    System.out.print("창고 ID: ");
    int warehouseId = scanner.nextInt();
    System.out.print("재고 수량: ");
    int quantity = scanner.nextInt();
    System.out.print("상태: ");
    String status = scanner.next();
    StockDto stock = new StockDto();
    stock.setProduct_id(productId);
    stock.setWarehouse_id(warehouseId);
    stock.setQuantity(quantity);
    stock.setStatus(status);
    stock.setCreated_at(LocalDateTime.now());
    stock.setUpdated_at(LocalDateTime.now());
    stockService.addStock(stock);

  }

  private void updateStock() {
    System.out.print("재고 ID: ");
    int id = scanner.nextInt();
    StockDto stock = stockService.getStockById(id);
    if (stock != null) {
      System.out.print("상품 ID (" + stock.getProduct_id() + "): ");
      String productId = scanner.next();
      //System.out.print("상품명 (" + stock.getProductName() + "): ");
      String productName = scanner.next();
      System.out.print("창고 ID (" + stock.getWarehouse_id() + "): ");
      int warehouseId = scanner.nextInt();
      System.out.print("재고 수량 (" + stock.getQuantity() + "): ");
      int quantity = scanner.nextInt();
      System.out.print("상태 (" + stock.getStatus() + "): ");
      String status = scanner.next();
      stock.setProduct_id(productId);
      stock.setWarehouse_id(warehouseId);
      stock.setQuantity(quantity);
      stock.setStatus(status);
      stock.getCreated_at();
      stock.setUpdated_at(LocalDateTime.now());
      stockService.updateStock(stock);
      System.out.println("재고가 업데이트되었습니다.");
    } else {
      System.out.println("재고를 찾을 수 없습니다.");
    }
  }

  private void deleteStock() {
    System.out.print("재고 ID: ");
    int id = scanner.nextInt();
    stockService.deleteStock(id);
    System.out.println("재고가 삭제되었습니다.");
  }
}
