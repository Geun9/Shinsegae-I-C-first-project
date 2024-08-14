package dto;

import common.ReleaseStatus;
import lombok.Getter;

import java.util.Date;

@Getter
public class ReleaseDto {

  private int id;
  private int shop_id;
  private String name;
  private int amount;
  private String customer_name;
  private String customer_address;
  private String remarks;
  private String product_code;
  private ReleaseStatus releaseStatus;
  //private Date 등록 날짜;
  //private Date 승인 날짜;
  //private Admin admin; //find할 때 저장용

  public ReleaseDto() {}

  public ReleaseDto(String customer_name, String customer_address, int amount, String product_code, ReleaseStatus releaseStatus, String remarks){
    this.customer_name = customer_name;
    this.customer_address = customer_address;
    this.amount = amount;
    this.product_code = product_code;
    this.releaseStatus = ReleaseStatus.PENDING;
    this.remarks = remarks;
  }
}
