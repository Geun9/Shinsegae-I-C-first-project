package service.serviceImpl;

import dao.daoImpl.DeliveryImplDao;
import dto.DeliveryDto;
import service.DeliveryService;

import java.util.List;

public class DeliveryServiceImpl implements DeliveryService {
    static DeliveryImplDao dao = new DeliveryImplDao();

    @Override
    public void createDelivery(DeliveryDto deliveryDto) {
        dao.createDelivery(deliveryDto);
    }

    @Override
    public void updateDelivery(int id) {
        dao.updateDelivery(id, 2);
    }

    @Override
    public List<DeliveryDto> getAll() {
        return dao.findByAll();
    }

    @Override
    public DeliveryDto getId(int id) {
        return dao.findByID(id);
    }

//    @Override
//    public void schedulerOn() {
//        dao.schedulerOn();
//    }
}