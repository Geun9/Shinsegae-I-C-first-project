package dao.daoImpl;

import config.ConnectionFactory;
import dao.ShippingInstructionDao;
import dto.ShippingInstructionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShippingInstructionImplDao implements ShippingInstructionDao {
    static ConnectionFactory factory = new ConnectionFactory().getInstance();
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    static Connection con = null;

    @Override
    public void createShippingInstruction(int delivery_id, int release_id) {
        con = factory.open();

        String query = "INSERT INTO ShippingInstruction (id, release_id, delivery_id, created_at, updated_at) VALUES(NULL,?,?,?,NULL)";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, release_id);
            pstmt.setInt(2, delivery_id);
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String created_at = dateTime.format(formatter);
            pstmt.setString(3, created_at);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("요청 완료");
    }
}
