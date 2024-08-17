package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class StockDto {

  private String t_category;
  private String i_category;
  private String s_category;
  private String p_name;
  private int warehouse_id;
  private String warehouse_name;
  private String address_city;
  private int quantity;

}