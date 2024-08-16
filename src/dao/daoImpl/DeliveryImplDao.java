package dao.daoImpl;

import config.ConnectionFactory;
import dao.DeliveryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryImplDao implements DeliveryDao {
    static ConnectionFactory factory = new ConnectionFactory().getInstance();
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    static Connection con = null;

    @Override
    public void createDelivery(int release_id){
//        배차 ID id
//        출고 ID release_id
//        회원 ID admin_id
//        유저 ID user_id
//        창고 ID warehouse_id
//        배송비 fee
//        배차 등록 날짜
//        배송 출발 날짜
//        도착 예정 날짜
//        요청 사항
//        배송 상태

         deliveryManCheck();

        con = factory.open();

        String query = "";
        try {
            pstmt = con.prepareStatement(query);


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
    public void deliveryManCheck() {
        //admin필요 -> 배송기사 조회
        con = factory.open();

        String query = "SELECT id FROM admin WHERE position = 'DELIVERY' AND status = 'WAIT' ORDER BY end_date ASC NULLS FIRST LIMIT 1";
        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()){

            }
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
