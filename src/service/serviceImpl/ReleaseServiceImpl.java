package service.serviceImpl;

import dao.daoImpl.ReleaseImplDao;
import dto.ReleaseDto;
import service.ReleaseService;

public class ReleaseServiceImpl implements ReleaseService {
  public void createRelease(ReleaseDto releaseDto){
    ReleaseImplDao releaseDao = new ReleaseImplDao();
    releaseDao.createRelease(releaseDto);//dao로 전달
  }
  public void updateRelease(int id){}
}
