package dao;

public interface ReleaseDao {
  boolean stockCheck();
  void statusRelease(int id, boolean check);
}