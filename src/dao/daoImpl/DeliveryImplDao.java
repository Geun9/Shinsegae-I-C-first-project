package dao.daoImpl;

import config.ConnectionFactory;
import dao.DeliveryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class DeliveryImplDao implements DeliveryDao {
    static ConnectionFactory factory = new ConnectionFactory().getInstance();
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    static Connection con = null;

    @Override
    public void createDelivery(int id){
        deliveryManCheck(id);

        con = factory.open();

        String query = "";
        try {
            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, 1);//user.getId(); -> user의 아이디
            pstmt.setString(2, releaseDto.getProduct_id());
            pstmt.setString(3, releaseDto.getCustomer_name());
            pstmt.setString(4, releaseDto.getCustomer_address());
            pstmt.setInt(5, releaseDto.getAmount());
            pstmt.setString(6, releaseDto.getReleaseStatus().name());
            pstmt.setString(7, releaseDto.getRemarks());
            SimpleDateFormat request_date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = request_date.format(new Date());
            pstmt.setString(8, formattedDate);

            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("배차가 등록되었습니다.");
    }
    @Override
    public void updateDelivery(int id, int select){

    }

    @Override
    public Queue<Admin> deliveryManCheck(int id) {
        //admin필요 -> 배송기사 조회
        Queue<Admin> qAdmin = new LinkedList<Admin>();
        con = factory.open();

        String query = "SELECT * FROM admin WHERE position = 'DELIVERY'";
        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()){
                int admin_id = rs.getInt("id");
                String name = rs.getString("name");
                String position = rs.getString("position");
                String department = rs.getString("department");

                // admin에서의 생성자
                Admin admin = new Admin(admin_id, name, position, department);
                qAdmin.add(admin);
            }
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return qAdmin;
    }
}
