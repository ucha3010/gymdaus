package com.gymdaus.core.service;


import com.gymdaus.core.entity.User;
import com.gymdaus.core.model.MainUserModel;
import com.gymdaus.core.model.TournamentRegistrationModel;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface MainService {

    List<MainUserModel> findByDni(String dni);

    void deleteTournamentRegistration(TournamentRegistrationModel tournamentRegistrationModel);

    User cargaBasicaCompleta(ModelAndView modelAndView);
}
