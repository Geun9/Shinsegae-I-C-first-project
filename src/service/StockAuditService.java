package service;

import dto.StockAuditDto;
import java.util.List;

/**
 * StockAuditService 재고 실사에 필요한 비즈니스 로직을 처리.
 */


public interface StockAuditService {
  List<StockAuditDto> getAllStockAudits();
  StockAuditDto getStockAuditById(int id);
  void addStockAudit(StockAuditDto stockAuditDto);
  void deleteStockAudit(int id);
}
