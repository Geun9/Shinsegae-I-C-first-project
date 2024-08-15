package service.serviceImpl;

import dao.daoImpl.StockAuditDaoImpl;
import dto.StockAuditDto;
import java.util.List;
import service.StockAuditService;

public class StockAuditServiceImpl implements StockAuditService {

  private final StockAuditDaoImpl stockAuditDao = StockAuditDaoImpl.getInstance();

  @Override
  public List<StockAuditDto> getAllStockAudits() {
    return stockAuditDao.findAll();
  }

  @Override
  public StockAuditDto getStockAuditById(int id) {
    return stockAuditDao.findById(id);
  }

  @Override
  public void addStockAudit(StockAuditDto stockAuditDto) {
    stockAuditDao.create(stockAuditDto);
  }

  @Override
  public void deleteStockAudit(int id) {
    stockAuditDao.delete(id);
  }
}
