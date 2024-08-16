package dto;

import common.DeliveryStatus;

import java.util.Queue;

public class DeliveryDto {
    private int admin_id;
    private DeliveryStatus deliveryStatus;
    private static final int fee = 5000;
    private String registation_date;
    private String start_date;
    private String end_date;
}
