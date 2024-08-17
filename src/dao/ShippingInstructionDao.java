package dao;

import dto.ShippingInstructionDto;

import java.util.List;

public interface ShippingInstructionDao {
    void createShippingInstruction();
    List<ShippingInstructionDto> findAll();
}
