package service.serviceImpl;

import dao.daoImpl.ShippingInstructionImplDao;
import service.ShippingInstructionService;

public class ShippingInstructionServiceImpl implements ShippingInstructionService {
    ShippingInstructionImplDao instructionImplDao = new ShippingInstructionImplDao();

    public void createShippingInstruction(){
        instructionImplDao.createShippingInstruction();
    }
}
