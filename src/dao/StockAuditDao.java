package dao;

import dto.StockAuditDto;
import dto.StockDto;
import java.util.List;

public interface StockAuditDao {
  /*Boolean createStockAudit(StockAuditDto stockAudit);

  Boolean deleteStockAudit(int num);*/


  List<StockAuditDto> findAll(); // 모든 재고 실사 조회
  StockAuditDto findById(int id); // ID로 재고 실사 조회
  Boolean create(StockAuditDto stockAuditDto); // 재고 실사 추가
  Boolean delete(int id); // 재고 실사 삭제
}
