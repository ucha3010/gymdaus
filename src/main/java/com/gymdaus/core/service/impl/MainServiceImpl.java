package com.gymdaus.core.service.impl;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.model.MainUserModel;
import com.gymdaus.core.model.TournamentRegistrationModel;
import com.gymdaus.core.service.MainService;
import com.gymdaus.core.service.Menu1Service;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service()
public class MainServiceImpl implements MainService {

    @Autowired
    private Menu1Service menu1Service;
    @Autowired
    private UserService userService;

    @Override
    public List<MainUserModel> findByDni(String dni) {

        MainUserModel mainUserModel;
/*        List<TournamentRegistrationModel> inscripcionList = tournamentRegistrationService.findByRegisteredIdCard(dni);
        inscripcionList.addAll(tournamentRegistrationService.findByAuthorizerIdCard(dni));*/
        List<MainUserModel> mainUserModelList = new ArrayList<>();
        /*if (!inscripcionList.isEmpty()) {
            inscripcionList.sort(Comparator.comparing(TournamentRegistrationModel::getRegistrationDate).reversed());
            for (TournamentRegistrationModel tournamentRegistrationModel : inscripcionList) {
                mainUserModel = new MainUserModel();
                mainUserModel.setId(tournamentRegistrationModel.getId());
                mainUserModel.setName(tournamentRegistrationModel.getRegisteredName());
                mainUserModel.setLastname(tournamentRegistrationModel.getRegistered1Lastname());
                mainUserModel.setSecondLastname(tournamentRegistrationModel.getRegistered2Lastname());
                mainUserModel.setRegistrationDate(tournamentRegistrationModel.getRegistrationDate());
                mainUserModel.setTournamentName(tournamentRegistrationModel.getTournamentName());
                mainUserModel.setTournamentDate(tournamentRegistrationModel.getTournamentDate());
                mainUserModel.setOwnRegistration(tournamentRegistrationModel.isRegistrationAdult());
                mainUserModelList.add(mainUserModel);
            }
        }*/
        LoggerMapper.methodOut(Level.INFO, Utils.obtenerNombreMetodo(), mainUserModelList, getClass());
        return mainUserModelList;
    }

    @Override
    public void deleteTournamentRegistration(TournamentRegistrationModel tournamentRegistrationModel) {
//        tournamentRegistrationService.delete(tournamentRegistrationModel);
        LoggerMapper.methodOut(Level.INFO, Utils.obtenerNombreMetodo(), tournamentRegistrationModel, getClass());
    }

    @Override
    public User cargaBasicaCompleta(ModelAndView modelAndView) {
        User usuario = userService.getLoggedUser();
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("menu1List", menu1Service.findAll());
        return usuario;
    }
}
