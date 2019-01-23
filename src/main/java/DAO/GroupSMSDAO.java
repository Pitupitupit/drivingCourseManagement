package DAO;

import POJO.GroupSMS;

import java.util.List;

public interface GroupSMSDAO {
    List<GroupSMS> list();
    void save(GroupSMS groupSMS);
}
