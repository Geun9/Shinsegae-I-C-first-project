package dto;

import common.ReleaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseDto {

  private int id;
  private int user_id;
  private String product_id;
  private String customer_name;
  private String customer_address;
  //private String user_name;
  private int amount;
  private ReleaseStatus releaseStatus;
  private String remarks;
  //시간 받기
  private String request_date;
  private String update_date;
  //private Admin admin; //find할 때 저장용

  public ReleaseDto(String customer_name, String customer_address, int amount, String product_id, ReleaseStatus releaseStatus, String remarks){
    this.customer_name = customer_name;
    this.customer_address = customer_address;
    this.amount = amount;
    this.product_id = product_id;
    this.releaseStatus = ReleaseStatus.PENDING;
    this.remarks = remarks;
  }
}
