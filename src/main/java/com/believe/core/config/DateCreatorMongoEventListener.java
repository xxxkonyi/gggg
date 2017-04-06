package com.believe.core.config;

import com.believe.core.domain.AbstractEntity;
import com.believe.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class DateCreatorMongoEventListener extends AbstractMongoEventListener<AbstractEntity> {

  @Override
  public void onBeforeConvert(BeforeConvertEvent<AbstractEntity> event) {

    AbstractEntity entity = event.getSource();

    if (StringUtils.isBlank(entity.getId())) {
      entity.setId(IdGenerator.generateId());
    }

    if (null == entity.getCreatedDate()) {
      entity.setCreatedDate(DateTime.now());
    }

  }
}
