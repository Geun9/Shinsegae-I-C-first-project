package service;


import dto.DeliveryDto;

import java.util.List;

public interface DeliveryService {
  void createDelivery(DeliveryDto deliveryDto);
  void updateDelivery(int id);
  void deleteDelivery(int id);
  List<DeliveryDto> getAll();
  //void schedulerOn();
}
