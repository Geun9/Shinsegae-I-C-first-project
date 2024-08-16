package dao;

import java.util.Queue;

public interface DeliveryDao {
  void createDelivery(int id);
  void updateDelivery(int id, int select);
  //Queue<Admin> deliveryManCheck(int id);
}