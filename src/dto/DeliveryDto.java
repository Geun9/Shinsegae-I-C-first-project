package dto;

import common.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private int id;
    private int release_id;
    private int admin_id;
    private int warehouse_id;
    private int user_id;
    private String created_at;
    private String updated_at;
    private String start_date;
    private String end_date;
    private String remarks;
    private DeliveryStatus deliveryStatus;

    public DeliveryDto(int release_id, String remarks){
        this.release_id = release_id;
        this.remarks = remarks;
    }
}
