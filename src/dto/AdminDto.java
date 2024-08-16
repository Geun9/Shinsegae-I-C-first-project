package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private int id;
    private String name;
    private String adminId;
    private String password;
    private String companyEmail;
    private String department = "DELIVERY";
    private String position = "ADMIN";
    private String phone;
    private String createdAt;
    private int authorizerId;
    private String updatedAt;

    public AdminDto(int adminId, String name, String position, String department) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.department = department;
    }
}