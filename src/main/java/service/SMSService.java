package service;

import POJO.SMS;
import POJO.User;

import java.util.List;

public interface SMSService {
    void save(SMS sms);
    List<SMS> list();
    List<SMS> listCheckTime(int minutesBefore);
    void updateSent(SMS sms);
    void sendSMSAll(User user, String text);
    void sendSMSInstructors(User user, String text);
    void sendSMSClients(User user, String text);
}
