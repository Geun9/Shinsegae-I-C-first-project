package service;

import dto.WaybillDto;

import java.util.List;

public interface WaybillService {
  public void createRelease(WaybillDto waybillDto);
  List<WaybillDto> getAll();
  WaybillDto getId(int id);
}
