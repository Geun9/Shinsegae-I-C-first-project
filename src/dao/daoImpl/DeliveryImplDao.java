package dao.daoImpl;

import common.DeliveryStatus;
import config.ConnectionFactory;
import dao.DeliveryDao;
import dto.DeliveryDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DeliveryImplDao implements DeliveryDao {
    static ConnectionFactory factory = new ConnectionFactory().getInstance();
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    //static CallableStatement cstmt = null;
    static Connection con = null;

    static Deque<Integer> waitDeliveryMan = new LinkedList<>();

    @Override
    public void createDelivery(DeliveryDto dto){
        ReleaseImplDao releaseImplDao = new ReleaseImplDao();
        boolean decide = existenceByRelease(dto.getRelease_id());

        if(decide) {
            waitDeliveryMan = waitDeliveryMan();

            if (waitDeliveryMan.isEmpty())
                System.out.println("배송 가능한 기사가 없습니다.");

            else {
                Integer deliveryManId = waitDeliveryMan.getFirst();
                if(deliveryManId != null) {
                    con = factory.open();

                    String query = "INSERT INTO delivery (id, admin_id, created_at, end_date, Release_id, remarks, start_date, status, updated_at, user_id, warehouse_id) " +
                                    "VALUES(NULL,?,?,?,?,?,?,?,NULL,?,?)";
                    try {
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, waitDeliveryMan.pollFirst());
                        LocalDateTime dateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String created_at = dateTime.format(formatter);
                        pstmt.setString(2, created_at);
                        dateTime = dateTime.plusDays(2);
                        DateTimeFormatter end = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String end_date = dateTime.format(end);
                        pstmt.setString(3, end_date);
                        pstmt.setInt(4, dto.getRelease_id());
                        pstmt.setString(5, dto.getRemarks());
                        dateTime = dateTime.minusDays(1);
                        DateTimeFormatter start = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String start_date = dateTime.format(start);
                        pstmt.setString(6, start_date);
                        pstmt.setString(7, DeliveryStatus.WAIT.toString());
                        pstmt.setInt(8, releaseImplDao.findById(dto.getRelease_id()).getUser_id());
                        pstmt.setInt(9, 1);
                        pstmt.executeUpdate();
                        pstmt.close();
                        con.close();
                        factory.close();
                    } catch (SQLException | NullPointerException e) {
                        e.printStackTrace();
                    }
                    System.out.println("배차가 등록되었습니다.");
                }
                else
                    System.out.println("배송 가능한 기사가 없습니다.");
            }
        }
        else
            System.out.println("배차 등록이 불가능한 아이디입니다.");
    }
    @Override
    public void updateDelivery(int id, int select){
        String query = "";

        con = factory.open();

        switch (select) {
            case 1 -> query = "UPDATE delivery SET status = 'CLEAR', updated_at = NOW() WHERE id = ?";
            case 2 -> query = "UPDATE delivery d INNER JOIN releases r ON d.release_id = r.id " +
                                "SET d.status = 'CANCEL', r.status = 'CANCEL' " +
                                 "WHERE d.id = ?";
        }

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void deleteDelivery(int id) {
//        updateDelivery(id,2);
//        con = factory.open();
//        String query = "{CALL DeleteDelivery()}";
//        try {
//            cstmt = con.prepareCall(query);
//            cstmt.execute();
//            cstmt.close();
//            con.close();
//            factory.close();
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        try {
//            DeliveryDto findId = findByID(id);
//            waitDeliveryMan.addFirst(findId.getAdmin_id());//취소했으니까 배송기사 다시 들어가야한다.
//        } catch (NullPointerException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public List<Integer> deliveryManAll() {
        List<Integer> deliveryManAll = new ArrayList<>();

        con = factory.open();

        String query = "SELECT id FROM admin WHERE department = 'DELIVERY'";
        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                deliveryManAll.add(id);
            }
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveryManAll;
    }

    public List<DeliveryDto> findByAll(){
        List<DeliveryDto> findAll = new ArrayList<>();

        con = factory.open();

        String query = "SELECT * FROM delivery";
        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int release_id = rs.getInt("release_id");
                int admin_id = rs.getInt("admin_id");
                int user_id = rs.getInt("user_id");
                int warehouse_id = rs.getInt("warehouse_id");
                String created_at = rs.getString("created_at");
                String updated_at = rs.getString("updated_at");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");
                String remarks = rs.getString("remarks");
                String status = rs.getString("status");

                DeliveryDto dto = new DeliveryDto(id,
                                                release_id,
                                                admin_id,
                                                warehouse_id,
                                                user_id,
                                                created_at,
                                                updated_at,
                                                start_date,
                                                end_date,
                                                remarks,
                                                DeliveryStatus.valueOf(status));
                findAll.add(dto);
            }
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findAll;
    }

    @Override
    public DeliveryDto findByID(int delivery_id) {
        DeliveryDto findId = null;

        con = factory.open();

        String query = "SELECT * FROM delivery WHERE id = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,delivery_id);
            rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int release_id = rs.getInt("release_id");
                int admin_id = rs.getInt("admin_id");
                int user_id = rs.getInt("user_id");
                int warehouse_id = rs.getInt("warehouse_id");
                String created_at = rs.getString("created_at");
                String updated_at = rs.getString("updated_at");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");
                String remarks = rs.getString("remarks");
                String status = rs.getString("status");

                findId = new DeliveryDto(id,
                        release_id,
                        admin_id,
                        warehouse_id,
                        user_id,
                        created_at,
                        updated_at,
                        start_date,
                        end_date,
                        remarks,
                        DeliveryStatus.valueOf(status));
            }
            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findId;
    }

    public Deque<Integer> waitDeliveryMan(){
        List<DeliveryDto> deliveryAll = findByAll(); //전체 배송 아이디
        List<Integer> deliveryManAll = deliveryManAll();//모든 배송기사

        //전체 배송리스트를 받아와 배송중인 배송기사는 빼주고 나머지 인원은 오래있는 순서대로 queue에 저장한다.
        waitDeliveryMan = new LinkedList<>(deliveryManAll);

        if(!deliveryAll.isEmpty()) {
            for (DeliveryDto deliveryDto : deliveryAll) {
                if (deliveryDto.getDeliveryStatus().toString().equals("CLEAR"))
                    waitDeliveryMan.add(deliveryDto.getAdmin_id());
                else if(deliveryDto.getDeliveryStatus().toString().equals("CANCEL"))
                    continue;
                else
                    waitDeliveryMan.remove(deliveryDto.getAdmin_id());
            }
        }

        return waitDeliveryMan;
    }

//    @Override
//    public void schedulerOn() {
//        try {
//            con = factory.open();
//            String query = "SET GLOBAL event_scheduler = ON;";
//            pstmt = con.prepareStatement(query);
//            pstmt.execute();
//            pstmt.close();
//            con.close();
//            factory.close();
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    public boolean existenceByRelease(int id){
        boolean result = false;
        con = factory.open();

        String query = "SELECT status FROM releases WHERE id = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            result = rs.next();

            while(rs.next()){
                String status = rs.getString("status");

                if(status.equals("APPROVAL"))
                    result = true;
            }

            pstmt.close();
            con.close();
            factory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
