package service;

import dto.ReleaseDto;

public interface ReleaseService {
  public void createRelease(ReleaseDto releaseDto);
  public void updateRelease(int id);
}
