package com.gymdaus.core.service.impl;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.entity.UserRole;
import com.gymdaus.core.model.UserRoleModel;
import com.gymdaus.core.repository.UserRepository;
import com.gymdaus.core.repository.UserRoleRepository;
import com.gymdaus.core.service.UserRoleService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service()
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<String> findByUsername(String username) {
        com.gymdaus.core.entity.User user = findUserByUsername(username);
        Set<UserRole> userRoleList = userRoleRepository.findByUser(user);
        List<String> roles = new ArrayList<>();
        for (UserRole userRole: userRoleList) {
            roles.add(userRole.getRole());
        }
        return roles;
    }

    @Override
    public UserRoleModel findByUser(User user) {
        Set<UserRole> roles = findRolesByUser(user);
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setUsername(user.getUsername());
        List<String> rolesString = new ArrayList<>();
        for (UserRole userRole : roles) {
            rolesString.add(userRole.getRole());
        }
        userRoleModel.setRoles(rolesString);
        return userRoleModel;
    }

    @Override
    public Set<UserRole> findRolesByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void delete(Long userRoleId) {
        userRoleRepository.deleteByUserRoleId(userRoleId);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username);
        for (UserRole userRole : user.getUserRole()) {
            userRoleRepository.delete(userRole);
        }
    }

    @Override
    public void actualizarRoles(UserRoleModel userRoleModel) {
        int elimino = 0;
        int mantengo = 0;
        int inserto = 0;
        User user = findUserByUsername(userRoleModel.getUsername());
        Set<UserRole> rolesViejos = findRolesByUser(user);
        List<String> rolesNuevos = userRoleModel.getRoles();
        for (UserRole rolViejo : rolesViejos) {
            if (!rolesNuevos.contains(rolViejo.getRole())) {
                userRoleRepository.delete(rolViejo);
                elimino++;
            } else {
                rolesNuevos.remove(rolViejo.getRole());
                mantengo++;
            }
        }
        UserRole userRole;
        for (String rolNuevo : rolesNuevos) {
            userRole = new UserRole();
            userRole.setRole(rolNuevo);
            userRole.setUser(user);
            userRoleRepository.save(userRole);
            inserto++;
        }
        LoggerMapper.log(Level.INFO, "actualizarRoles", "Inserto: " + inserto + ", mantengo: " + mantengo + ", elimino: " + elimino, getClass());
    }

    @Override
    public List<UserRole> adminAvailableRoles() {
         return Arrays.asList(new UserRole(null, Constants.ROLE_USER), new UserRole(null, Constants.ROLE_ADMIN));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
