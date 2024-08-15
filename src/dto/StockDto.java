package dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StockDto {

  /*private int id;               // 재고 ID
  private String product_id;       // 상품 ID
  private String product_name;   // 상품명
  private String warehouse_name; // 창고명
  private int section;       // 창고 섹션
  private int cell;          // 창고 셀
  private int quantity;         // 재고 수량
  private String status;        // 상태 (출고 예정 / 보관)
  private LocalDateTime created_at;  // 최초 등록 시각
  private LocalDateTime updated_at;  // 최종 수정 시각*/

  private int id; // 재고 ID
  private String product_id; // 상품 ID
  private String product_name; // 상품명
  private int warehouse_id; // 창고 ID
  private int quantity; // 재고 수량
  private String status; // 상태 (출고 예정/보관)
  private LocalDateTime created_at; // 최초 등록 시각
  private LocalDateTime updated_at; // 최종 수정 시각

}