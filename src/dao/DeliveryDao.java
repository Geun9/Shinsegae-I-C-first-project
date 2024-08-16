package dao;

public interface DeliveryDao {
  void createDelivery(int id);
  void updateDelivery(int id, int select);
  void deliveryManCheck();
}