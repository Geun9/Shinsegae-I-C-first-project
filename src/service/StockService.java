package service;


import dto.StockDto;
import java.util.List;

public interface StockService {
  List<StockDto> getAllStocks();
  StockDto getStockById(int id);
  void addStock(StockDto stockDto);
  void updateStock(StockDto stockDto);
  void deleteStock(int id);
  List<StockDto> getStocksByCategory(int category_id);
  List<StockDto> getStockByWarehouse(int warehouse_id);
}
