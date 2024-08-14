package dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    private String name;
    private String businessNumber;
    private String companyName;
    private String representative;
    private String userId;
    private String password;
    private String email;
    private String companyEmail;
    private String phone;
    private String companyPhone;
    private String fax;
    private String zipCode;
    private String address;
    private int isUnregister;
    private String createdAt;
    private String updatedAt;
    private int updatedAdminId;
    private String updatedAdminAt;
    private String unregisteredAt;

    public UserDto(String name, String businessNumber, String companyName, String representative,
        String userId, String password, String email, String companyEmail, String phone,
        String companyPhone, String fax, String zipCode,
        String address) {
        this.name = name;
        this.businessNumber = businessNumber;
        this.companyName = companyName;
        this.representative = representative;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.companyEmail = companyEmail;
        this.phone = phone;
        this.companyPhone = companyPhone;
        this.fax = fax;
        this.zipCode = zipCode;
        this.address = address;
        this.createdAt = getTime();
    }

    private String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}