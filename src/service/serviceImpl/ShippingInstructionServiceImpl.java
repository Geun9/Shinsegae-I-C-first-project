package service.serviceImpl;

import dao.daoImpl.ShippingInstructionImplDao;
import dto.ShippingInstructionDto;
import service.ShippingInstructionService;

import java.util.List;

public class ShippingInstructionServiceImpl implements ShippingInstructionService {
    ShippingInstructionImplDao instructionImplDao = new ShippingInstructionImplDao();

    public void createShippingInstruction(){
        instructionImplDao.createShippingInstruction();
    }

    @Override
    public List<ShippingInstructionDto> getAll() {
        return instructionImplDao.findAll();
    }
}
