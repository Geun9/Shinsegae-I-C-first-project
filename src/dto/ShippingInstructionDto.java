package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingInstructionDto {
    private int id;
    private int release_id;
    private int delivery_id;
    private String created_at;
    private String updated_at;
}
