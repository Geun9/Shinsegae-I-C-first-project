package dao.daoImpl;

import config.ConnectionFactory;
import dao.ShippingInstructionDao;
import dto.ShippingInstructionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShippingInstructionImplDao implements ShippingInstructionDao {
    static ConnectionFactory factory = new ConnectionFactory().getInstance();
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    static Connection con = null;

    @Override
    public void createShippingInstruction() {
        con = factory.open();

        String query = "CREATE TRIGGER after_delivery_insert " +
                "AFTER INSERT ON delivery " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    INSERT INTO shippinginstruction (releaseId, deliveryId, created_at) " +
                "    VALUES (NEW.releaseId, NEW.id, NOW()); " +
                "END;";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.execute();
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("요청 완료");
    }

    @Override
    public List<ShippingInstructionDto> findAll() {
        List<ShippingInstructionDto> shipAll = new ArrayList<>();
        con = factory.open();

        String query = "SELECT * FROM ShippingInstruction";
        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                int delivery_id = rs.getInt("deliveryId");
                int release_id = rs.getInt("releaseId");
                String created_at = rs.getString("created_at");

                ShippingInstructionDto dto = new ShippingInstructionDto(id, delivery_id, release_id, created_at);
                shipAll.add(dto);
            }
            pstmt.close();
            rs.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            System.err.println("커넥션 에러");
        }

        return shipAll;
    }
}