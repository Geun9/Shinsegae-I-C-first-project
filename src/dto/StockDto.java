package dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StockDto {
  int id;
  String product_id;
  int warehouse_id;
  int quantity;
  Enum status;
  LocalDateTime created_at;
  LocalDateTime updated_at;
}
