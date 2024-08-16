package dao.daoImpl;

import common.ReleaseStatus;
import config.ConnectionFactory;
import dao.ReleaseDao;
import dto.ReleaseDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReleaseImplDao implements ReleaseDao {

  static ConnectionFactory factory = new ConnectionFactory().getInstance();
  static ResultSet rs = null;
  static PreparedStatement pstmt = null;
  static Connection con = null;

  public void createRelease(ReleaseDto releaseDto){
    if(!productCheck(releaseDto.getProduct_id())) {
      System.out.println("주문코드 잘못 입력 또는 해당 상품이 창고에 존재하지 않습니다.");
      return;
    }
//    findby를 가져오기 위함
//    UserDao dao = new UserDao();

//    id는 final로 되어있으며 id값의 유저 정보들을 db에서 가지고와 admin 필드에 저장
//    user = dao.findById(id);
    con = factory.open();

      String query = "INSERT INTO releases (id, user_id, product_id, customer_name, customer_address, amount, status, remarks, request_date) VALUES(NULL,?,?,?,?,?,?,?,?)";
    try {
      pstmt = con.prepareStatement(query);

      pstmt.setInt(1, 1);//user.getId(); -> user의 아이디
      pstmt.setString(2, releaseDto.getProduct_id());
      pstmt.setString(3, releaseDto.getCustomer_name());
      pstmt.setString(4, releaseDto.getCustomer_address());
      pstmt.setInt(5, releaseDto.getAmount());
      pstmt.setString(6, releaseDto.getReleaseStatus().name());
      pstmt.setString(7, releaseDto.getRemarks());
      LocalDateTime dateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      String request_date = dateTime.format(formatter);
      pstmt.setString(8, request_date);

      pstmt.executeUpdate();
      pstmt.close();
      con.close();
      factory.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("요청 완료");
  }

  public List<ReleaseDto> findAll(){
    List<ReleaseDto> releaseAll = new ArrayList<>();
    con = factory.open();

    String query = "SELECT * FROM releases";
    try {
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()){
        int id = rs.getInt("id");
        int user_id = rs.getInt("user_id");
        String product_id = rs.getString("product_id");
        String customer_name = rs.getString("customer_name");
        String customer_address = rs.getString("customer_address");
        int amount = rs.getInt("amount");
        ReleaseStatus Status = ReleaseStatus.valueOf(rs.getString("status"));
        String remarks = rs.getString("remarks");
        String request_date = rs.getString("request_date");
        String update_date = rs.getString("approval_date");

        ReleaseDto dto = new ReleaseDto(id, user_id, product_id, customer_name, customer_address, amount, Status, remarks, request_date, update_date);
        releaseAll.add(dto);
      }

      pstmt.close();
      rs.close();
      con.close();
      factory.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }

    return releaseAll;
  }

  //재고 수량과 출고하고 싶은 상품 수량 비교하는 부분
  @Override
  public int stockCheck(int id) {
    int cal = -2;
    ReleaseDto findDto = findById(id);

    if(findDto.getReleaseStatus() != ReleaseStatus.PENDING){
      return cal;
    }
    con = factory.open();

    String query = "SELECT quantity FROM stock s INNER JOIN product p on p.id = ? and s.product_id = p.id";

    try {
      pstmt = con.prepareStatement(query);
      pstmt.setString(1,findDto.getProduct_id());
      rs = pstmt.executeQuery();

      while(rs.next()) {
        int stock = rs.getInt("quantity");

        if (stock > findDto.getAmount())
          cal = stock - findDto.getAmount();
        else
          cal = -1;
      }

      pstmt.close();
      rs.close();
      con.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }
    return cal;
}

  //status를 바꿔주는 부분
  @Override
  public void updateRelease(int id, int select){
    String query = "UPDATE releases SET status = ?, approval_date = ? WHERE id = ?";
    con = factory.open();

    try {
      pstmt = con.prepareStatement(query);
      if(select == 1)
        pstmt.setString(1, ReleaseStatus.APPROVED.toString());
      else if(select == 2)
        pstmt.setString(1, ReleaseStatus.REJECTED.toString());
      else
        pstmt.setString(1, ReleaseStatus.CANCEL.toString());
      LocalDateTime dateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      String approval_date = dateTime.format(formatter);
      pstmt.setString(2, approval_date);
      pstmt.setInt(3, id);

      pstmt.executeUpdate();

      pstmt.close();
      con.close();
      factory.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }
  }

  public boolean productCheck (String product_id){
    boolean check = false;

    con = factory.open();

    String query = "SELECT COUNT(*) FROM stock s INNER JOIN product p ON s.product_id = p.id and p.id = ?";
    try {
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, product_id);//상품이 창고에 있는지 확인
      rs = pstmt.executeQuery();

      int count = 0;
      if (rs.next())
        count = rs.getInt(1);

      if(count > 0)
        check = true;

      rs.close();
      pstmt.close();
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return check;
  }

  public ReleaseDto findById(int id) {
      ReleaseDto findDto = null;
      con = factory.open();

      String query = "SELECT * FROM releases WHERE id = ?";
      try {
          pstmt = con.prepareStatement(query);
          pstmt.setInt(1, id);
          rs = pstmt.executeQuery();

          while(rs.next()) {
            int release_id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            String product_id = rs.getString("product_id");
            String customer_name = rs.getString("customer_name");
            String customer_address = rs.getString("customer_address");
            int amount = rs.getInt("amount");
            ReleaseStatus Status = ReleaseStatus.valueOf(rs.getString("status"));
            String remarks = rs.getString("remarks");
            String request_date = rs.getString("request_date");
            String update_date = rs.getString("approval_date");

            findDto = new ReleaseDto(
                    release_id,
                    user_id,
                    product_id,
                    customer_name,
                    customer_address,
                    amount,
                    Status,
                    remarks,
                    request_date,
                    update_date);
          }

          rs.close();
          pstmt.close();
          con.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
      return findDto;
  }
}

//@Override
//public int stockCheck(int id) {
//  int cal = -2;
//  ReleaseDto findDto = findById(id);
//
//  if(findDto.getReleaseStatus() != ReleaseStatus.PENDING){
//    return cal;
//  }
//  con = factory.open();
//
//  //String query = "SELECT quantity FROM stock s INNER JOIN product p on p.id = ? and s.product_id = p.id";
//  query1.append("{call stockCheck(?,?,?,?)}");
//
//  try {
//    cstmt = con.prepareCall(query1.toString());
//    cstmt.setInt(1, 1);
//    cstmt.setString(2, "a");
//    cstmt.setInt(3, findDto.getAmount());
//    cstmt.registerOutParameter(4, Types.INTEGER);
//    cstmt.execute(); //실행
//    cal = cstmt.getInt(4);
//    cstmt.close();
//    con.close();
//  } catch (SQLException e) {
//    System.err.println("커넥션 에러");
//  }
//  return cal;
//}
//