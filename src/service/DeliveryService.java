package service;

import dto.ReleaseDto;

public interface DeliveryService {
  public void createRelease(ReleaseDto releaseDto);
  public void updateRelease(int id);
  public void approvalRelease();
  public void stockCheck();
}
