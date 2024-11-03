package com.gymdaus.core.mapper;

import com.gymdaus.core.entity.UserDocumentManager;
import com.gymdaus.core.model.UserDocumentManagerModel;
import org.springframework.stereotype.Component;

@Component
public class MapperUserDocumentManager {

    public UserDocumentManagerModel entity2Model(UserDocumentManager externObject) {
        UserDocumentManagerModel localObject = null;
        if (externObject != null) {
            localObject = new UserDocumentManagerModel();
            localObject.setId(externObject.getId());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setCreationDate(externObject.getCreationDate());
            localObject.setDeleteDate(externObject.getDeleteDate());
            localObject.setExtension(externObject.getExtension());
            localObject.setFilename(externObject.getFilename());
            localObject.setPath(externObject.getPath());
            localObject.setUsername(externObject.getUsername());
            localObject.setName(externObject.getName());
        }
        return localObject;
    }

    public UserDocumentManager model2Entity(UserDocumentManagerModel externObject) {
        UserDocumentManager localObject = new UserDocumentManager();
        if (externObject != null) {
            localObject.setId(externObject.getId());
            localObject.setEnabled(externObject.isEnabled());
            localObject.setCreationDate(externObject.getCreationDate());
            localObject.setDeleteDate(externObject.getDeleteDate());
            localObject.setExtension(externObject.getExtension());
            localObject.setFilename(externObject.getFilename());
            localObject.setPath(externObject.getPath());
            localObject.setUsername(externObject.getUsername());
            localObject.setName(externObject.getName());
        }
        return localObject;
    }
}
