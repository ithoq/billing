package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.UserRoleAssembler;
import com.elstele.bill.dao.interfaces.UserActivityDAO;
import com.elstele.bill.dao.interfaces.UserRoleDAO;
import com.elstele.bill.datasrv.interfaces.UserRoleDataService;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.utils.Enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserRoleDataServiceImpl implements UserRoleDataService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    @Transactional
    public String saveRole(UserRoleForm form){
        UserRoleAssembler assembler = new UserRoleAssembler();
        UserRole role = assembler.fromFormToBean(form);
        if(form.isNew()){
            return checkBeforeCreate(form, role);
        }
        else{
            userRoleDAO.update(role);
             return "userrole.success.update";
        }
    }

    private String checkBeforeCreate(UserRoleForm form, UserRole role) {
        UserRole roleByName = userRoleDAO.getByName(form.getName());
        if (roleByName == null) {
            return creatingNew(role);
        }
        return restoreOrCreate(roleByName);
    }

    private String creatingNew(UserRole role){
        role.setStatus(Status.ACTIVE);
        userRoleDAO.create(role);
        return "userrole.success.add";
    }

    private String restoreOrCreate(UserRole roleByName){
        if (roleByName.getStatus() == Status.DELETED) {
            userRoleDAO.setStatus(roleByName.getId(), Status.ACTIVE);
            return "userrole.success.restored";
        } else {
            return "userrole.error.create";
        }
    }

    @Override
    @Transactional
    public void deleteRole(Integer id){
        userRoleDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public List<UserRoleForm> listUserRole(){
        List<UserRoleForm> result = new ArrayList<UserRoleForm>();
        UserRoleAssembler assembler = new UserRoleAssembler();

        List<UserRole> beans = userRoleDAO.listUserRole();
        if(beans != null) {
            for (UserRole curBean : beans) {
/*
                UserRoleForm curForm = assembler.fromBeanToForm(curBean);
                ArrayList<Integer> activityList = new ArrayList<Integer>();
                for (Activity activity : curBean.getActivities()) {
                    activityList.add(activity.getId());
                }
                curForm.setActivityId(activityList);
*/

                result.add(assembler.fromBeanToForm(curBean));
            }
        }
        return result;
    }

    @Override
    @Transactional
    public UserRole findById(Integer id){
        return userRoleDAO.getById(id);
    }

    @Override
    @Transactional
    public UserRoleForm getUserRoleFormById(Integer id){
        UserRoleAssembler assembler = new UserRoleAssembler();
        UserRoleForm result = null;
        UserRole bean = userRoleDAO.getById(id);
        if (bean != null){
            UserRoleForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }

}
