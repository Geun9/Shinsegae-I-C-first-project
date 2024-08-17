package dao;

import dto.StockRequestDto;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StockRequestDao {

  void create(StockRequestDto stockRequest) throws IOException, SQLException;

  ArrayList<StockRequestDto> findByAll() throws SQLException;
  ArrayList<StockRequestDto> findByStatus() throws SQLException;


  boolean updateStatus(ArrayList<Integer> updateList) throws SQLException;

}
