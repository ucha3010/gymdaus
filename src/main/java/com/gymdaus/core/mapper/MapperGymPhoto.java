package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymPhoto;
import com.gymdaus.core.model.GymModel;
import com.gymdaus.core.model.GymPhotoModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymPhoto {

    public GymPhotoModel entity2Model(GymPhoto externObject) {
        GymPhotoModel localObject = new GymPhotoModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setCreationDate(externObject.getCreationDate());
            localObject.setDeleteDate(externObject.getDeleteDate());
            localObject.setExtension(externObject.getExtension());
            localObject.setFilename(externObject.getFilename());
            localObject.setPath(externObject.getPath());
            localObject.setSection(externObject.getSection());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public GymPhoto model2Entity(GymPhotoModel externObject) {
        GymPhoto localObject = new GymPhoto();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setCreationDate(externObject.getCreationDate());
            localObject.setDeleteDate(externObject.getDeleteDate());
            localObject.setExtension(externObject.getExtension());
            localObject.setFilename(externObject.getFilename());
            localObject.setPath(externObject.getPath());
            localObject.setSection(externObject.getSection());
            if (externObject.getGymModel() != null) {
                localObject.setGymId(externObject.getGymModel().getId());
            } else {
                localObject.setGymId(0L);
            }
        }
        return localObject;
    }
}
