package dao;

import dto.ReleaseDto;

public interface ReleaseDao {
  int stockCheck(int id);
  void createRelease(ReleaseDto releaseDto);
  void updateRelease(int id, int select);
}