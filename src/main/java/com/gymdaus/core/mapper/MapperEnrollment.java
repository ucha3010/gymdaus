package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.Enrollment;
import com.gymdaus.core.model.*;
import org.springframework.stereotype.Component;

@Component
public class MapperEnrollment {

    public EnrollmentModel entity2Model(Enrollment externObject) {
        EnrollmentModel localObject = new EnrollmentModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setEnrollmentKind(externObject.getEnrollmentKind());
            localObject.setOwn(externObject.isOwn());
            localObject.setMinor(externObject.isMinor());
            localObject.setInclusive(externObject.isInclusive());
            localObject.setEnrollmentDate(externObject.getEnrollmentDate());
            localObject.setName(externObject.getName());
            localObject.setTournamentDate(externObject.getTournamentDate());
            localObject.setGymName(externObject.getGymName());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setAddressCountry(externObject.getAddressCountry());
            localObject.setUserEnrollmentName(externObject.getUserEnrollmentName());
            localObject.setUserEnrollmentLastname(externObject.getUserEnrollmentLastname());
            localObject.setUserEnrollmentSecondLastname(externObject.getUserEnrollmentSecondLastname());
            localObject.setUserEnrollmentIdCard(externObject.getUserEnrollmentIdCard());
            localObject.setUserEnrollmentBirthdate(externObject.getUserEnrollmentBirthdate());
            localObject.setUserEnrollmentSex(externObject.getUserEnrollmentSex());
            localObject.setAuthorizerEnrollmentAddressStreet(externObject.getAuthorizerEnrollmentAddressStreet());
            localObject.setAuthorizerEnrollmentAddressNumber(externObject.getAuthorizerEnrollmentAddressNumber());
            localObject.setAuthorizerEnrollmentAddressOther(externObject.getAuthorizerEnrollmentAddressOther());
            localObject.setAuthorizerEnrollmentAddressCity(externObject.getAuthorizerEnrollmentAddressCity());
            localObject.setAuthorizerEnrollmentAddressZip(externObject.getAuthorizerEnrollmentAddressZip());
            localObject.setAuthorizerEnrollmentAddressCountry(externObject.getAuthorizerEnrollmentAddressCountry());
            localObject.setAuthorizerEnrollmentName(externObject.getAuthorizerEnrollmentName());
            localObject.setAuthorizerEnrollmentLastname(externObject.getAuthorizerEnrollmentLastname());
            localObject.setAuthorizerEnrollmentSecondLastname(externObject.getAuthorizerEnrollmentSecondLastname());
            localObject.setAuthorizerEnrollmentIdCard(externObject.getAuthorizerEnrollmentIdCard());
            localObject.setAuthorizerEnrollmentBirthdate(externObject.getAuthorizerEnrollmentBirthdate());
            localObject.setAuthorizerEnrollmentAs(externObject.getAuthorizerEnrollmentAs());
            localObject.setEmail(externObject.getEmail());
            localObject.setPhone(externObject.getPhone());
            localObject.setPaid(externObject.isPaid());
            localObject.setPaidDate(externObject.getPaidDate());
            localObject.setSepaDirectDebit(externObject.isSepaDirectDebit());
            localObject.setSepaAccountNumber(externObject.getSepaAccountNumber());
            localObject.setSepaAccountPerson(externObject.getSepaAccountPerson());
            localObject.setSwift(externObject.getSwift());
            localObject.setSigned(externObject.isSigned());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
            if (externObject.getUsername() != null) {
                UserModel userModel = new UserModel();
                userModel.setUsername(externObject.getUsername());
                localObject.setUserModel(userModel);
            }
            if (externObject.getGymActivityId() != 0) {
                GymActivityModel gymActivityModel = new GymActivityModel();
                gymActivityModel.setId(externObject.getGymActivityId());
                localObject.setGymActivityModel(gymActivityModel);
            }
            if (externObject.getGymMoreRegistrationId() != 0) {
                GymMoreRegistrationModel gymMoreRegistrationModel = new GymMoreRegistrationModel();
                gymMoreRegistrationModel.setId(externObject.getGymMoreRegistrationId());
                localObject.setGymMoreRegistrationModel(gymMoreRegistrationModel);
            }
        }
        return localObject;
    }

    public Enrollment model2Entity(EnrollmentModel externObject) {
        Enrollment localObject = new Enrollment();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setEnrollmentKind(externObject.getEnrollmentKind());
            localObject.setOwn(externObject.isOwn());
            localObject.setMinor(externObject.isMinor());
            localObject.setInclusive(externObject.isInclusive());
            localObject.setEnrollmentDate(externObject.getEnrollmentDate());
            localObject.setName(externObject.getName());
            localObject.setTournamentDate(externObject.getTournamentDate());
            localObject.setGymName(externObject.getGymName());
            localObject.setAddressStreet(externObject.getAddressStreet());
            localObject.setAddressNumber(externObject.getAddressNumber());
            localObject.setAddressOther(externObject.getAddressOther());
            localObject.setAddressCity(externObject.getAddressCity());
            localObject.setAddressZip(externObject.getAddressZip());
            localObject.setAddressCountry(externObject.getAddressCountry());
            localObject.setUserEnrollmentName(externObject.getUserEnrollmentName());
            localObject.setUserEnrollmentLastname(externObject.getUserEnrollmentLastname());
            localObject.setUserEnrollmentSecondLastname(externObject.getUserEnrollmentSecondLastname());
            localObject.setUserEnrollmentIdCard(externObject.getUserEnrollmentIdCard());
            localObject.setUserEnrollmentBirthdate(externObject.getUserEnrollmentBirthdate());
            localObject.setUserEnrollmentSex(externObject.getUserEnrollmentSex());
            localObject.setAuthorizerEnrollmentAddressStreet(externObject.getAuthorizerEnrollmentAddressStreet());
            localObject.setAuthorizerEnrollmentAddressNumber(externObject.getAuthorizerEnrollmentAddressNumber());
            localObject.setAuthorizerEnrollmentAddressOther(externObject.getAuthorizerEnrollmentAddressOther());
            localObject.setAuthorizerEnrollmentAddressCity(externObject.getAuthorizerEnrollmentAddressCity());
            localObject.setAuthorizerEnrollmentAddressZip(externObject.getAuthorizerEnrollmentAddressZip());
            localObject.setAuthorizerEnrollmentAddressCountry(externObject.getAuthorizerEnrollmentAddressCountry());
            localObject.setAuthorizerEnrollmentName(externObject.getAuthorizerEnrollmentName());
            localObject.setAuthorizerEnrollmentLastname(externObject.getAuthorizerEnrollmentLastname());
            localObject.setAuthorizerEnrollmentSecondLastname(externObject.getAuthorizerEnrollmentSecondLastname());
            localObject.setAuthorizerEnrollmentIdCard(externObject.getAuthorizerEnrollmentIdCard());
            localObject.setAuthorizerEnrollmentBirthdate(externObject.getAuthorizerEnrollmentBirthdate());
            localObject.setAuthorizerEnrollmentAs(externObject.getAuthorizerEnrollmentAs());
            localObject.setEmail(externObject.getEmail());
            localObject.setPhone(externObject.getPhone());
            localObject.setPaid(externObject.isPaid());
            localObject.setPaidDate(externObject.getPaidDate());
            localObject.setSepaDirectDebit(externObject.isSepaDirectDebit());
            localObject.setSepaAccountNumber(externObject.getSepaAccountNumber());
            localObject.setSepaAccountPerson(externObject.getSepaAccountPerson());
            localObject.setSwift(externObject.getSwift());
            localObject.setSigned(externObject.isSigned());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
            if (externObject.getUserModel() != null) {
                localObject.setUsername(externObject.getUserModel().getUsername());
            }
            if (externObject.getGymActivityModel() != null) {
                localObject.setGymActivityId(externObject.getGymActivityModel().getId());
            } else {
                localObject.setGymActivityId(0L);
            }
            if (externObject.getGymMoreRegistrationModel() != null) {
                localObject.setGymMoreRegistrationId(externObject.getGymMoreRegistrationModel().getId());
            } else {
                localObject.setGymMoreRegistrationId(0L);
            }
        }
        return localObject;
    }
}
