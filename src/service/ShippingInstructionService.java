package service;

import dto.ShippingInstructionDto;

import java.util.List;

public interface ShippingInstructionService {
    void createShippingInstruction();
    List<ShippingInstructionDto> getAll();
}
