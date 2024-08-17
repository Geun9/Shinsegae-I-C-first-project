package dao;


import dto.StockDto;
import java.util.List;

public interface StockDao {

 // Boolean createUserStock();
 // Boolean createAdminStock();
  List<StockDto> findStockAdmin();
  List<StockDto> findStockUser(int uID);
}
