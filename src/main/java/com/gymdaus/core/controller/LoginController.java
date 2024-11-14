package com.gymdaus.core.controller;

import com.gymdaus.core.exception.SenderException;
import com.gymdaus.core.model.PasswordModel;
import com.gymdaus.core.model.TokenModel;
import com.gymdaus.core.model.UserModel;
import com.gymdaus.core.service.EmailService;
import com.gymdaus.core.service.TokenService;
import com.gymdaus.core.service.impl.UserService;
import com.gymdaus.core.util.Constants;
import com.gymdaus.core.util.LoggerMapper;
import com.gymdaus.core.util.Utils;
import jakarta.persistence.PersistenceException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

@Controller
@RequestMapping("/login-page")
public class LoginController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public String loginPage(Model model,
                            @RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "logout", required = false) String logout) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), model, getClass());
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), model, getClass());
        return Constants.LOGIN;
    }

    //TODO hasta acá lo nuevo

    @GetMapping("/forgot-pass")
    @PreAuthorize("permitAll()")
    public ModelAndView forgotPass(ModelAndView modelAndView) {
        LoggerMapper.methodIn(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        modelAndView.setViewName("formForgotPass");
        if (modelAndView.isEmpty() || !modelAndView.getModel().containsKey("userPasswordModel")) {
            modelAndView.addObject("userPasswordModel", new PasswordModel());
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/new-pass")
    @PreAuthorize("permitAll()")
    public ModelAndView newPass(@ModelAttribute("passwordModel") PasswordModel passwordModel, ModelAndView modelAndView) {
        try {
            UserModel userModel = userService.findModelByUsername(passwordModel.getUsername());
            TokenModel tokenModel = new TokenModel();
            tokenModel.setId(UUID.randomUUID().toString());
            tokenModel.setUsername(passwordModel.getUsername());
            tokenModel.setExpiration(Utils.addSubtractMinutes(15));
            tokenService.add(tokenModel);
            emailService.sendChangePassword(userModel, tokenModel);
            modelAndView.addObject("emailEnvio", "Se ha enviado un correo para el cambio de " +
                    "contraseña a " + Utils.obfuscate(userModel.getEmail()));
        } catch (PersistenceException | SenderException e) {
            mostrarExcepcion(e, passwordModel, modelAndView);
        }
        modelAndView.addObject("passwordModel", passwordModel);
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return forgotPass(modelAndView);
    }

    @GetMapping("/pass-new")
    @PreAuthorize("permitAll()")
    public ModelAndView passNew(@RequestParam String username, @RequestParam String token, ModelAndView modelAndView) {
        if (Utils.isNullOrEmpty(username) || Utils.isNullOrEmpty(token)) {
            modelAndView.addObject("avisoKO", "Problemas con la redirección");
            modelAndView.setViewName(Constants.LOGIN);
            LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
            return modelAndView;
        }
        tokenService.deleteExpired();
        TokenModel tokenModel = tokenService.findById(token);
        UserModel userModel = userService.findModelByUsername(username);
        if (tokenModel.getId() == null || Utils.isBeforeNow(tokenModel.getExpiration())
                || userModel.getUsername() == null || !userModel.getUsername().equals(tokenModel.getUsername())
            /*|| userModel.getCodigoGimnasio() != tokenModel.getCodigoGimnasio()*/) {
            modelAndView.addObject("avisoKO", "El intento de cambio de contraseña expiró o superó su límite");
            modelAndView.setViewName(Constants.LOGIN);
            LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
            return modelAndView;
        }
        modelAndView.addObject("tokenModel", tokenModel);
        modelAndView.setViewName("formularioNuevaClave");
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    @PostMapping("/change-pass")
    @PreAuthorize("permitAll()")
    public ModelAndView changePass(@ModelAttribute("tokenModel") TokenModel tokenModel, ModelAndView modelAndView) {
        modelAndView.setViewName(Constants.LOGIN);
        if (Utils.isNullOrEmpty(tokenModel.getUsername()) || Utils.isNullOrEmpty(tokenModel.getId())
                || Utils.isNullOrEmpty(tokenModel.getPassword())) {
            modelAndView.addObject("avisoKO", "Problemas con la redirección");
            LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
            return modelAndView;
        }
        tokenService.deleteExpired();
        TokenModel tokenModelBBDD = tokenService.findById(tokenModel.getId());
        UserModel userModel = userService.findModelByUsername(tokenModel.getUsername());
        if (tokenModelBBDD.getId() == null || Utils.isBeforeNow(tokenModelBBDD.getExpiration())
                || userModel.getUsername() == null || !userModel.getUsername().equals(tokenModelBBDD.getUsername())
            /*|| userModel.getCodigoGimnasio() != tokenModelBBDD.getCodigoGimnasio()*/) {
            modelAndView.addObject("avisoKO", "El intento de cambio de contraseña expiró o superó su límite");
            LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
            return modelAndView;
        }
        userModel.setPassword(userService.encodePassword(tokenModel.getPassword()));
        userModel.setModificationUsername(tokenModel.getUsername());
        try {
            userService.updatePass(userModel);
            tokenService.delete(tokenModel.getId());
            modelAndView.addObject("avisoOK", "Contraseña modificada correctamente");
        } catch (PersistenceException pe) {
            LoggerMapper.log(Level.ERROR, "changePass", pe.getMessage(), getClass());
            modelAndView.addObject("avisoKO", "Problemas al guardar la contraseña. Por favor inténtelo de nuevo.");
        }
        LoggerMapper.methodOut(Level.INFO, Utils.getMethodName(), modelAndView, getClass());
        return modelAndView;
    }

    private void mostrarExcepcion(Exception e, PasswordModel passwordModel, ModelAndView modelAndView) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        LoggerMapper.log(Level.ERROR, "nuevaClave", sw.toString(), getClass());
        modelAndView.addObject("errorEnvio", "No se ha podido enviar el cambio de contraseña " +
                "al usuario " + passwordModel.getUsername() + ". Por favor contacte con el administrador.");
    }

}
