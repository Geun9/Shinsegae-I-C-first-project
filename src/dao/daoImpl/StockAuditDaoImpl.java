package dao.daoImpl;

import config.ConnectionFactory;
import dao.StockAuditDao;
import dao.StockDao;
import dto.StockAuditDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StockAuditDaoImpl implements StockAuditDao {
  private final StockDao stockDao = new StockDaoImpl();
  private static StockAuditDaoImpl instance;

  StockAuditDaoImpl() {
    // 싱글톤하기 위해 선언.
  }

  public static synchronized StockAuditDaoImpl getInstance() {
    if (instance == null) {
      instance = new StockAuditDaoImpl();
    }
    return instance;
  }



  @Override
  public List<StockAuditDto> findAll() {
    List<StockAuditDto> stockAuditDtos = new ArrayList<>();
    String sql = "SELECT * FROM StockAudit";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try{
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()){
        StockAuditDto stockAuditDto = new StockAuditDto();
        stockAuditDto.setId(rs.getInt("id"));
        stockAuditDto.setProduct_id(rs.getString("product_id"));
        stockAuditDto.setWarehouse_id(rs.getInt("warehouse_id"));
        stockAuditDto.setStockrequest_id(rs.getInt("stockrequest_id"));
        stockAuditDto.setRelease_id(rs.getInt("release_id"));
        stockAuditDto.setQuantity(rs.getInt("quantity"));
        stockAuditDto.setStatus(rs.getString("status"));
        stockAuditDto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        stockAuditDtos.add(stockAuditDto);
      } // while end
      rs.close();
    }
    catch (SQLException e) {
      System.out.println("정보를 가지고 오지 못하였습니다.");
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          ConnectionFactory.getInstance().close();
          conn.close();
        }
      } catch (SQLException e) {
        System.out.println("에러 발생");
      }
    } // finally end
    return stockAuditDtos;
  }

  @Override
  public StockAuditDto findById(int id) {
    StockAuditDto stockAuditDto = null;
    String sql = "SELECT * FROM StockAudit WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try{
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      pstmt.setInt(1, id);

      if(rs.next()){
        stockAuditDto = new StockAuditDto();
        stockAuditDto.setId(rs.getInt("id"));
        stockAuditDto.setProduct_id(rs.getString("product_id"));
        stockAuditDto.setWarehouse_id(rs.getInt("warehouse_id"));
        stockAuditDto.setStockrequest_id(rs.getInt("stockrequest_id"));
        stockAuditDto.setRelease_id(rs.getInt("release_id"));
        stockAuditDto.setQuantity(rs.getInt("quantity"));
        stockAuditDto.setStatus(rs.getString("status"));
        stockAuditDto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
      }
      rs.close();
    }catch (SQLException e) {
      System.out.println("해당 ID의 정보를 가지고 오지 못하였습니다.");
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          ConnectionFactory.getInstance().close();
          conn.close();
        }
      } catch (SQLException e) {
        System.out.println("에러 발생");
      }
    } // finally end
    return stockAuditDto;
  }

  @Override
  public Boolean create(StockAuditDto stockAuditDto) {
    String sql = "INSERT INTO StockAudit (product_id, warehouse_id, "
        + "stockrequest_id, release_id, quantity, status, created_at) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try{
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, stockAuditDto.getProduct_id());
      pstmt.setInt(2, stockAuditDto.getWarehouse_id());
      pstmt.setInt(3, stockAuditDto.getStockrequest_id());
      pstmt.setInt(4, stockAuditDto.getRelease_id());
      pstmt.setInt(5, stockAuditDto.getQuantity());
      pstmt.setString(6, stockAuditDto.getStatus());
      pstmt.setTimestamp(7, Timestamp.valueOf(stockAuditDto.getCreated_at()));
      pstmt.executeUpdate();

      /// 재고 수량 업데이트 (입고이면 증가, 출고이면 감소)
      int deltaQuantity = stockAuditDto.getStatus().equals("입고") ? stockAuditDto.getQuantity() : -stockAuditDto.getQuantity();
      stockDao.updateStockQuantity(stockAuditDto.getProduct_id(), deltaQuantity);
    }catch (SQLException e) {
      System.out.println("수량 수정 오류 발생!!!!");
      return false;
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          ConnectionFactory.getInstance().close();
          conn.close();
        }
      } catch (SQLException e) {
        System.out.println("에러 발생");
      }
    } // finally end
    return true;
  }

  @Override
  public Boolean delete(int id) {
    String sql = "DELETE FROM StockAudit WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try{
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, id);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("재고 실사 삭제 오류 발생!!!!");
      return false;
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          ConnectionFactory.getInstance().close();
          conn.close();
        }
      } catch (SQLException e) {
        System.out.println("에러 발생");
      }
    } // finally end

    return true;
  }
}
