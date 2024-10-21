package com.gymdaus.core.service;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.entity.UserRole;
import com.gymdaus.core.model.UserRoleModel;

import java.util.List;
import java.util.Set;

public interface UserRoleService {

    List<String> findByUsername(String username);

    UserRoleModel findByUser(User user);

    Set<UserRole> findRolesByUser(User user);

    void save(UserRole userRole);

    void delete(Long userRoleId);

    void deleteByUsername(String username);

    void actualizarRoles(UserRoleModel userRoleModel);

    List<UserRole> adminAvailableRoles();

}
