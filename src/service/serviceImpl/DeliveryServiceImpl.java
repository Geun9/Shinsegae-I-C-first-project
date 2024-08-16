package service.serviceImpl;

import dao.daoImpl.DeliveryImplDao;
import service.DeliveryService;

public class DeliveryServiceImpl implements DeliveryService {
    static DeliveryImplDao dao = new DeliveryImplDao();

    @Override
    public void createDelivery(int id) {
        dao.createDelivery(id);
    }

    @Override
    public void updateDelivery(int id) {

    }
}
