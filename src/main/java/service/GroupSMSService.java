package service;

import POJO.GroupSMS;

import java.util.List;

public interface GroupSMSService {
    List<GroupSMS> list();
    void save(GroupSMS groupSMS);
}
