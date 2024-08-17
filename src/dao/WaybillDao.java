package dao;

import dto.WaybillDto;

import java.util.List;

public interface WaybillDao {
    void createWaybill(WaybillDto waybillDto);
    List<WaybillDto> findByAll();
    WaybillDto findById(int id);
}
