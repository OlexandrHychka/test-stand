package com.gmail.maksimus40a.test.stand.features.base.interfaces;

import com.gmail.maksimus40a.test.stand.features.base.IdEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends IdEntity> {

    int countOfEntities();

    List<T> getAllEntities();

    Optional<T> getEntityById(Integer id);

    List<T> getEntitiesByCriteria(String criteria, long limit);

    T addEntity(T book);

    Optional<T> editEntity(Integer id, T editedEntity);

    Optional<T> deleteEntityById(Integer id);
}