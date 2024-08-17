package dao;

import dto.DeliveryDto;

import java.util.Deque;
import java.util.List;

public interface DeliveryDao {
  void createDelivery(DeliveryDto deliveryDto);
  void updateDelivery(int id, int select);
  //void deleteDelivery(int id);
  boolean existenceByRelease(int id);
  List<DeliveryDto> findByAll();
  DeliveryDto findByID(int delivery_id);
  List<Integer> deliveryManAll();
  Deque<Integer> waitDeliveryMan();
  //void schedulerOn();
}