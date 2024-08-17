package dao.daoImpl;

import config.ConnectionFactory;
import dao.StockDao;
import dto.StockDto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDaoImpl implements StockDao {

  private static StockDaoImpl instance;

  private StockDaoImpl() {
    // 싱글톤을 위해 구현
  }

  public static synchronized StockDaoImpl getInstance() {
    if (instance == null) {
      instance = new StockDaoImpl();
    }
    return instance;
  }

  @Override
  public List<StockDto> findStockAdmin() {
    return executeStockProcedure("{call selectAdminStock(?,?,?,?,?,?,?,?)}", null);
  }

  @Override
  public List<StockDto> findStockUser(int uID) {
    return executeStockProcedure("{call selectUserStock(?)}", uID);
  }


  private List<StockDto> executeStockProcedure(String sql, Integer uID) {
    List<StockDto> stockDtos = new ArrayList<>();

    Connection conn = null;
    CallableStatement cstmt = null;
    ResultSet rs = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      cstmt = conn.prepareCall(sql);

      if (uID != null) {
        cstmt.setInt(1, uID); // 사용자 ID를 설정
      }

      rs = cstmt.executeQuery();
      while (rs.next()) {
        StockDto stockDto = new StockDto(
            rs.getString("t_category"),
            rs.getString("i_category"),
            rs.getString("s_category"),
            rs.getString("pname"),
            rs.getInt("WID"),
            rs.getString("wname"),
            rs.getString("address_city"),
            rs.getInt("quantity")
        );
        stockDtos.add(stockDto);
      }
    } catch (SQLException e) {
      System.out.println("SQL 에러 발생 : " + e.getMessage());
    } finally {
      try {
        if (rs != null) rs.close();
        if (cstmt != null) cstmt.close();
        if (conn != null) {
          ConnectionFactory.getInstance().close();
          conn.close();
        }
      } catch (SQLException e) {
        System.out.println("자원 해제 중 오류 발생 : " + e.getMessage());
      }
    }
    return stockDtos;
  }

}