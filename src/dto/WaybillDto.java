package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaybillDto {
    private int id;
    private String waybill_number;
    private int delivery_id;
    private int release_id;
    private String created_at;
    private String updated_at;

    public WaybillDto(String waybill_number, int delivery_id){
        this.waybill_number = waybill_number;
        this.delivery_id = delivery_id;
    }
}
