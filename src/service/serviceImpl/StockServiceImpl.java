package service.serviceImpl;

import dao.StockDao;
import dao.daoImpl.StockDaoImpl;
import dto.StockDto;
import java.util.List;
import service.StockService;

/**
 *  StockService 재고 관리에 필요한 비즈니스 로직을 처리
 */

public class StockServiceImpl implements StockService {

  private final StockDaoImpl stockDao = StockDaoImpl.getInstance();

  @Override
  public List<StockDto> getAllStocks() {
    return stockDao.findAll();
  }

  @Override
  public StockDto getStockById(int id) {
    return stockDao.findById(id);
  }

  @Override
  public void addStock(StockDto stockDto) {
    stockDao.create(stockDto);
  }

  @Override
  public void updateStock(StockDto stockDto) {
    stockDao.update(stockDto);
  }

  @Override
  public void deleteStock(int id) {
    stockDao.delete(id);
  }

  @Override
  public List<StockDto> getStocksByCategory(int category_id) {
    return stockDao.findByCategory(category_id);
  }

  @Override
  public List<StockDto> getStockByWarehouse(int warehouse_id) {
    return stockDao.findByWarehouse(warehouse_id);
  }
}
