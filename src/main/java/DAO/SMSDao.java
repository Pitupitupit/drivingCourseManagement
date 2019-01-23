package DAO;

import POJO.SMS;

import java.util.List;

public interface SMSDao {
    void save(SMS sms);
    List<SMS> list();
    List<SMS> listCheckTime(int minutesBefore);
    void updateSent(SMS sms);
}
