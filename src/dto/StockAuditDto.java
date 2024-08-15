package dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StockAuditDto {

/*  private int id;               // 재고 실사 ID
  private String product_id;       // 상품 ID
  private String product_name;   // 상품명
  private String warehouse_name; // 창고명
  private int section;       // 창고 섹션
  private int cell;          // 창고 셀
  private int audit_quantity;    // 재고 실사 수량 (입고/출고 수량)
  private int stock_quantity;    // 재고 수량 (현재 총 재고 수량)
  private String status;        // 실사 상태 (입고/출고)
  private LocalDateTime created_At;  // 최초 등록 시각*/

  private int id; // 재고 실사 ID
  private String product_id; // 상품 ID
  private String product_name; // 상품명
  private int warehouse_id; // 창고 ID
  private int stockrequest_id; // 입고 ID
  private int release_id; // 출고 ID
  private int quantity; // 재고 실사 수량
  private String status; // 실사 상태 (입고/출고)
  private LocalDateTime created_at; // 최초 등록 시각

}