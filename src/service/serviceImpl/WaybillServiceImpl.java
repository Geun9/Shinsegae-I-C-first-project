package service.serviceImpl;

import dao.WaybillDao;
import dao.daoImpl.WaybillImplDao;
import dto.WaybillDto;
import service.WaybillService;

import java.util.List;

public class WaybillServiceImpl implements WaybillService {
    static WaybillImplDao dao = new WaybillImplDao();

    @Override
    public void createRelease(WaybillDto waybillDto) {
        dao.createWaybill(waybillDto);
    }

    public List<WaybillDto> getAll(){
        return dao.findByAll();
    }

    public WaybillDto getId(int id){
        return dao.findById(id);
    }
}
