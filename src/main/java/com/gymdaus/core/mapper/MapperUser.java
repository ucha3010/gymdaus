package com.gymdaus.core.mapper;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.CountryService;
import com.gymdaus.core.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {


    @Autowired
    private CountryService countryService;

    @Autowired
    private UserRoleService userRoleService;

    public UserModel entity2Model(User externObject) {
        UserModel localObject = new UserModel();
        if (externObject != null) {
            localObject.setUsername(externObject.getUsername());
            localObject.setPassword(externObject.getPassword());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setName(externObject.getName());
            localObject.setLastname(externObject.getLastname());
            localObject.setSecondLastname(externObject.getSecondLastname());
            localObject.setSex(externObject.getSex());
            localObject.setBirthdate(externObject.getBirthdate());
            localObject.setCountryModel(countryService.findById(externObject.getCountryId()));
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUsername(externObject.getModificationUsername());
            localObject.setEmail(externObject.getEmail());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setPhone(externObject.getPhone());
            localObject.setUserRoles(userRoleService.findByUsername(externObject.getUsername()));
        }

        return localObject;
    }

    public User model2Entity(UserModel externObject) {
        User localObject = new User();
        if (externObject != null) {
            localObject.setUsername(externObject.getUsername());
            localObject.setPassword(externObject.getPassword());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setName(externObject.getName());
            localObject.setLastname(externObject.getLastname());
            localObject.setSecondLastname(externObject.getSecondLastname());
            localObject.setSex(externObject.getSex());
            localObject.setBirthdate(externObject.getBirthdate());
            if (externObject.getCountryModel() != null) {
                localObject.setCountryId(externObject.getCountryModel().getId());
            }
            localObject.setRegistrationDate(externObject.getRegistrationDate());
            localObject.setModificationDate(externObject.getModificationDate());
            localObject.setModificationUsername(externObject.getModificationUsername());
            localObject.setEmail(externObject.getEmail());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setPhone(externObject.getPhone());
        }
        return localObject;
    }
}
