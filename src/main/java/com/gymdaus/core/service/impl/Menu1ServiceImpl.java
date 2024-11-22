package com.gymdaus.core.service.impl;

import com.gymdaus.core.model.Menu1Model;
import com.gymdaus.core.service.Menu1Service;
import com.gymdaus.core.service.Menu2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class Menu1ServiceImpl implements Menu1Service {
    @Autowired
    private Menu2Service menu2Service;

    @Override
    public Menu1Model findMenu1GymList() {
        Menu1Model menu1Model = new Menu1Model();
        menu1Model.setName("menu1.gyms");
        menu1Model.setMenu2ModelList(menu2Service.findAllGymList());
        return menu1Model;
    }

    @Override
    public Menu1Model findMenu1ActivityList() {
        Menu1Model menu1Model = new Menu1Model();
        menu1Model.setName("menu1.activities");
        menu1Model.setMenu2ModelList(menu2Service.findAllActivityList());
        return menu1Model;
    }

    @Override
    public Menu1Model findMenu1MoreRegistrationList() {
        Menu1Model menu1Model = new Menu1Model();
        menu1Model.setName("menu1.more.registrations");
        menu1Model.setMenu2ModelList(menu2Service.findAllMoreRegistrationList());
        return menu1Model;
    }
}
