package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.GymDocumentManager;
import com.gymdaus.core.model.GymDocumentManagerModel;
import com.gymdaus.core.model.GymModel;
import org.springframework.stereotype.Component;

@Component
public class MapperGymDocumentManager {

    public GymDocumentManagerModel entity2Model(GymDocumentManager externObject) {
        GymDocumentManagerModel localObject = new GymDocumentManagerModel();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setCreationDate(externObject.getCreationDate());
            localObject.setDeleteDate(externObject.getDeleteDate());
            localObject.setExtension(externObject.getExtension());
            localObject.setFilename(externObject.getFilename());
            localObject.setPath(externObject.getPath());
            localObject.setGymName(externObject.getGymName());
            localObject.setSigned(externObject.isSigned());
            localObject.setSection(externObject.getSection());
            if (externObject.getGymId() != 0) {
                GymModel gymModel = new GymModel();
                gymModel.setId(externObject.getGymId());
                localObject.setGymModel(gymModel);
            }
        }
        return localObject;
    }

    public GymDocumentManager model2Entity(GymDocumentManagerModel externObject) {
        GymDocumentManager localObject = new GymDocumentManager();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setCreationDate(externObject.getCreationDate());
            localObject.setDeleteDate(externObject.getDeleteDate());
            localObject.setExtension(externObject.getExtension());
            localObject.setFilename(externObject.getFilename());
            localObject.setPath(externObject.getPath());
            localObject.setGymName(externObject.getGymName());
            localObject.setSigned(externObject.isSigned());
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
