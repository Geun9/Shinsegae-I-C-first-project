package dao.daoImpl;

import config.ConnectionFactory;
import dao.StockDao;
import dto.StockDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockDaoImpl implements StockDao {

  private static StockDaoImpl instance;

  StockDaoImpl() {
    // 싱글톤하기 위해 선언.
  }

  public static synchronized StockDaoImpl getInstance() {
    if (instance == null) {
      instance = new StockDaoImpl();
    }
    return instance;
  }

  // 전체 재고조회
  @Override
  public List<StockDto> findAll() {
    List<StockDto> stockDtos = new ArrayList<>();
    String sql = "SELECT * FROM Stock";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        StockDto stock = new StockDto();
        stock.setId(rs.getInt("id"));
        stock.setProduct_id(rs.getString("product_id"));
        stock.setWarehouse_id(rs.getInt("warehouse_id"));
        stock.setQuantity(rs.getInt("quantity"));
        stock.setStatus(rs.getString("status"));
        stock.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        stock.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        stockDtos.add(stock);
      } // while end
      rs.close();
    } catch (SQLException e) {
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

    return stockDtos;
  }

  @Override
  public StockDto findById(int id) {
    StockDto stockDto = null;
    String sql = "SELECT * FROM Stock WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      pstmt.setInt(1, id);

      if (rs.next()) {
        stockDto = new StockDto();
        stockDto.setId(rs.getInt("id"));
        stockDto.setProduct_id(rs.getString("product_id"));
        stockDto.setWarehouse_id(rs.getInt("warehouse_id"));
        stockDto.setQuantity(rs.getInt("quantity"));
        stockDto.setStatus(rs.getString("status"));
        stockDto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        stockDto.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
      } // if end

      rs.close();
    } catch (SQLException e) {
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
    return stockDto;
  }

  @Override
  public Boolean create(StockDto stock) {
    String sql = "INSERT INTO Stock (product_id, warehouse_id, quantity, status, created_at, updated_at) VALUES (?,?,?,?,?,?)";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, stock.getProduct_id());
      pstmt.setInt(2, stock.getWarehouse_id());
      pstmt.setInt(3, stock.getQuantity());
      pstmt.setString(4, stock.getStatus());
      pstmt.setTimestamp(5, Timestamp.valueOf(stock.getCreated_at()));
      pstmt.setTimestamp(6, Timestamp.valueOf(stock.getUpdated_at()));
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("재고 생성을 하지 못하였습니다.");
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
  public Boolean update(StockDto stock) {
    String sql = "UPDATE Stock SET product_id = ?, warehouse_id = ?, quantity = ?, status = ?, updated_at = ? WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, stock.getProduct_id());
      pstmt.setInt(2, stock.getWarehouse_id());
      pstmt.setInt(3, stock.getQuantity());
      pstmt.setString(4, stock.getStatus());
      pstmt.setTimestamp(5, Timestamp.valueOf(stock.getUpdated_at()));
      pstmt.setInt(6, stock.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("재고 수정 오류 발생!!!!!!");
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
    String sql = "DELETE FROM Stock WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, id);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("재고 삭제 오류 발생!!!!");
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


  // 재고 수량을 증가 또는 감소
  @Override
  public Boolean updateStockQuantity(String productId, int deltaQuantity){
    String sql = "UPDATE Stock SET quantity = quantity + ?, updated_at = ? WHERE id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, deltaQuantity);
      pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
      pstmt.setString(3, productId);
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("재고 수량 수정 오류 발생!!!!");
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
  public List<StockDto> findByWarehouse(int warehouseId){
    List<StockDto> stockDtos = new ArrayList<>();
    String sql = "SELECT * FROM Stock WHERE warehouse_id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try{
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      pstmt.setInt(1, warehouseId);
      while(rs.next()){
        StockDto stockDto = new StockDto();
        stockDto.setId(rs.getInt("id"));
        stockDto.setProduct_id(rs.getString("product_id"));
        stockDto.setWarehouse_id(rs.getInt("warehouse_id"));
        stockDto.setQuantity(rs.getInt("quantity"));
        stockDto.setStatus(rs.getString("status"));
        stockDto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        stockDto.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        stockDtos.add(stockDto);
      } // while end
      rs.close();
    }  catch (SQLException e) {
      System.out.println("창고 조회 오류 발생!!!!");
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
    return stockDtos;
  }

  // 전체 재고 조회
  /*@Override
  public List<StockDto> findAll() {
    List<StockDto> stockList = new ArrayList<>();
    String sql = "SELECT * FROM Stock";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        StockDto stock = new StockDto();
        stock.setId(rs.getInt("id"));
        stock.setWarehouse_name(rs.getString("warehouse_name"));
        stock.setProduct_id(rs.getString("product_id"));
        stock.setProduct_name(rs.getString("product_name"));
        stock.setSection(rs.getInt("section_id"));
        stock.setCell(rs.getInt("cell_id"));
        stock.setQuantity(rs.getInt("quantity"));
        stock.setStatus(rs.getString("status"));
        stock.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        stock.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        stockList.add(stock);
      } // while end
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("재고 정보를 가져 오지 못하였습니다.", e);
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          ConnectionFactory.getInstance().close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return stockList;
  }
*/
  // 대분류별 재고 조회
  @Override
  public List<StockDto> findByCategory(int categoryId) {
    List<StockDto> stockList = new ArrayList<>();
    String sql = """
        SELECT 
            s.id AS 재고ID,
            p.category_name AS 카테고리명,
            w.name AS 창고명,
            w.section AS 섹션ID,
            w.cell AS 셀ID,
            p.id   AS 상품 ID,
            p.name AS 상품명,
            s.quantity AS 재고수량,
            s.status AS 상태,
            s.created_at AS 최초등록시각,
            s.updated_at AS 최종수정시각 
        FROM
            Stock s
        JOIN 
            Warehouse w ON s.warehouse_id = w.id
        JOIN 
            Product p ON s.product_id = p.id
        WHERE 
            p.category_id = ?;
        """;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = ConnectionFactory.getInstance().open();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, categoryId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        StockDto stock = new StockDto();
        stock.setId(rs.getInt("id"));
       /* stock.setCategory_name(rs.getString("category_name"));
        stock.setProduct_id(rs.getString("product_id"));
        */
        stock.setWarehouse_id(rs.getInt("warehouse_id"));
        // stock.setWarehouse_name(rs.getString("warehouse_name"));
        /*stock.setSection_id(rs.getInt("section_id"));
        stock.setCell_id(rs.getInt("cell_id"));
        */
        stock.setQuantity(rs.getInt("quantity"));
        stock.setStatus(rs.getString("status"));
        stock.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        stock.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        stockList.add(stock);
      } // while end
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("카테고리 정보를 가져오지 못 하였습니다.", e);
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          ConnectionFactory.getInstance().close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } // finally end
    return stockList;
  }

  // 중분류별 재고 조회
  @Override
  public List<StockDto> findStockMiddleCategory(int middleCategoryId) {
    return findByCategory(middleCategoryId); // 대분류 구현부에서 카테고리 id를 입력해서 해당 분류에 맞게 조회를 하기 때문에 로직이 같음.
  }

  // 소분류별 재고 조회
  @Override
  public List<StockDto> findStockSubCategory(int subCategoryId) {
    return findByCategory(subCategoryId); // // 대분류 구현부에서 카테고리 id를 입력해서 해당 분류에 맞게 조회를 하기 때문에 로직이 같음.
  }

}