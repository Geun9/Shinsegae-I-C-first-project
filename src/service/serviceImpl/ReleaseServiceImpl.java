package service.serviceImpl;

import dao.daoImpl.ReleaseImpl;
import dto.ReleaseDto;
import service.ReleaseService;

public class ReleaseServiceImpl implements ReleaseService {
  public void createRelease(ReleaseDto releaseDto){
    ReleaseImpl releaseDao = new ReleaseImpl();
    releaseDao.createRelease(releaseDto);//dao로 전달
  }
  public void updateRelease(int id){}
}
