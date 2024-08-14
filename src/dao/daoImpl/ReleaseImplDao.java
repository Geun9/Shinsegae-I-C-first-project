package dao.daoImpl;

import config.ConnectionFactory;
import dao.ReleaseDao;
import dto.ReleaseDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReleaseImplDao implements ReleaseDao {

  static ConnectionFactory factory = new ConnectionFactory().getInstance();
  static ResultSet rs = null;
  static PreparedStatement pstmt = null;
  static Connection con = null;

  //출고 요청서를 만드는 부분(상품의 재고와 관계없이 출고 요청서가 만들어짐)
  public void createRelease(ReleaseDto releaseDto){
    //유저 필드
    //Admin admin = new admin();

    //findby를 가져오기 위함
    //AdminDao dao = new AdminDao();

    //id는 final로 되어있으며 id값의 유저 정보들을 db에서 가지고와 admin 필드에 저장
    //admin = dao.findById(id);

    con = factory.open();

    //유저 이름, 요청인(유저이름), 상품 이름, 종류, 개수, 출고 상태,
    String query = "INSERT TABLE user VALUES(?,?,?,?,?,?,?,?,?,?)";
    try {
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();

      //final pk(id -> user)를 받고 유저의 정보를 저장 후 테이블에 insert 한다.

      pstmt.close();
      rs.close();
      con.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }
  }

  public List<ReleaseDto> findAll(){
    List<ReleaseDto> releaseAll = new ArrayList<>();
    con = factory.open();

    String query = "SELECT * FROM release";
    try {
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();

      while(rs.next()){
        String name = rs.getString("name");
        int value = rs.getInt("value");

        ReleaseDto dto = new ReleaseDto(생략);
        releaseAll.add(dto);
      }

      pstmt.close();
      rs.close();
      con.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }

    return releaseAll;
  }

  //재고 수량과 출고하고 싶은 상품 수량 비교하는 부분
  @Override
  public boolean stockCheck() {
    boolean check = false;
    con = factory.open();

    String query = "SELECT quantity FROM stock s INNER JOIN product p on s.id = p.id";
    try {
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();

      int stock = rs.getInt("quantity");

      if(stock >  amount)
        check = true;

      pstmt.close();
      rs.close();
      con.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }

    return check;
  }

  //status를 바꿔주는 부분
  @Override
  public void statusRelease(int id, boolean check){
    con = factory.open();

    String query = "UPDATE release SET status = 'reject' WHERE id = ?";
    try {
      pstmt = con.prepareStatement(query);
      rs = pstmt.executeQuery();

      // ?를 여기서 구현
      // 테이블 업데이트

      pstmt.close();
      rs.close();
      con.close();
    } catch (SQLException e) {
      System.err.println("커넥션 에러");
    }
  }
}