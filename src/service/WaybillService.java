package service;

import dto.ReleaseDto;

public interface WaybillService {
  public void createRelease(ReleaseDto releaseDto);
  public void updateRelease(int id);
  public void approvalRelease();
  public void stockCheck();
}
