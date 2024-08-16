package service.serviceImpl;

import dao.daoImpl.ReleaseImplDao;
import dto.ReleaseDto;
import service.ReleaseService;

import java.util.ArrayList;
import java.util.List;

public class ReleaseServiceImpl implements ReleaseService {
  static ReleaseImplDao releaseDao = new ReleaseImplDao();

  public void createRelease(ReleaseDto releaseDto){
    releaseDao.createRelease(releaseDto);//dao로 전달
  }

  public List<ReleaseDto> getAll(){
      return releaseDao.findAll();
  }

  public void updateRelease(int id, int select){
      releaseDao.updateRelease(id,select);
  }

  public int stockCheck(int id){
    return releaseDao.stockCheck(id);
  }
}
