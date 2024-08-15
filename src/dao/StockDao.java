package dao;

import dto.StockDto;
import java.util.List;

public interface StockDao {

 /* // 전체 재고 조회
  List<StockDto> findAll();

  // 대분류별 재고 조회
  List<StockDto> findStockCategory(int categoryId);

  // 중분류별 재고 조회
  List<StockDto> findStockMiddleCategory(int middleCategoryId);

  // 소분류별 재고 조회
  List<StockDto> findStockSubCategory(int subCategoryId);

  Boolean createStock(StockDto stockdto);

  Boolean deleteStock(int num);

  Boolean updateStock(StockDto stockDto, int num);*/


  List<StockDto> findAll(); // 모든 재고 조회
  StockDto findById(int id); // ID로 재고 조회
  Boolean create(StockDto stock); // 재고 추가
  Boolean update(StockDto stock); // 재고 업데이트
  Boolean delete(int id); // 재고 삭제
  List<StockDto> findByCategory(int category_id); // 카테고리별 재고 조회
  List<StockDto> findStockMiddleCategory(int middleCategoryId); // 중분류
  List<StockDto> findStockSubCategory(int subCategoryId); // 소분류
  Boolean updateStockQuantity(String productId, int delta_quantity); // 재고 수량 업데이트
  List<StockDto> findByWarehouse(int warehouse_id); // 창고별 재고 실사 조회
}
