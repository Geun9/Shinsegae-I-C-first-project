package dto;

import common.ReleaseStatus;
import lombok.Getter;

@Getter
public class ReleaseDto {

  private int id;
  private int shop_id;
  private String name;
  private int amount;
  private String customer_name;
  private String customer_address;
  private String product_code;
  private ReleaseStatus releaseStatus;

  public ReleaseDto(String customer_name, String customer_address, int amount, String product_code, ReleaseStatus releaseStatus){
    this.customer_name = customer_name;
    this.customer_address = customer_address;
    this.amount = amount;
    this.product_code = product_code;
    this.releaseStatus = ReleaseStatus.PENDING;
  }

  public ReleaseDto(String customer_name, String customer_address, int amount, String name, int shop_id, ReleaseStatus releaseStatus) {
    this.customer_name = customer_name;
    this.customer_address = customer_address;
    this.amount = amount;
    this.name = name;
    this.shop_id = shop_id;
    this.releaseStatus = ReleaseStatus.PENDING;
  }
}
