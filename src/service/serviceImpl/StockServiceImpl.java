package service.serviceImpl;

import dao.daoImpl.StockDaoImpl;
import dto.StockDto;
import java.util.List;
import service.StockService;

/**
 * StockService 재고 관리에 필요한 비즈니스 로직을 처리
 */
public class StockServiceImpl implements StockService {
  private static StockServiceImpl instance;

  private StockServiceImpl() {}

  public static StockServiceImpl getInstance() {  // 싱글톤
    if (instance == null) {
      instance = new StockServiceImpl();
    }
    return instance;
  }

  private StockDaoImpl stockDao = StockDaoImpl.getInstance();

  // 전체 재고 조회
  public List<StockDto> getAllStock() {
    return stockDao.findStockAdmin();
  }

  // 특정 사용자의 재고 조회
  public List<StockDto> getUserStock(int uID) {
    return stockDao.findStockUser(uID);
  }

  // 대분류, 중분류, 소분류에 따른 재고 조회
  public List<StockDto> getFilteredStock(String category, int type) {
    List<StockDto> stockDtos = stockDao.findStockAdmin();
    return stockDtos.stream()
        .filter(stockDto -> (type == 0) ||
            (type == 1 && stockDto.getT_category().contains(category)) ||
            (type == 2 && stockDto.getI_category().contains(category)) ||
            (type == 3 && stockDto.getS_category().contains(category)))
        .toList();
  }
}
