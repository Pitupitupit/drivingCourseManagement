package service;


import DAO.GroupSMSDAO;
import POJO.GroupSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupSMSServiceImp implements GroupSMSService{
    @Autowired
    GroupSMSDAO groupSMSDAO;

    @Override
    public List<GroupSMS> list() {
        return groupSMSDAO.list();
    }

    @Override
    public void save(GroupSMS groupSMS) {
        groupSMSDAO.save(groupSMS);
    }
}
