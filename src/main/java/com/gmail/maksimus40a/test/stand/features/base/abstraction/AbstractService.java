package com.gmail.maksimus40a.test.stand.features.base.abstraction;

import com.gmail.maksimus40a.test.stand.features.base.IdEntity;
import com.gmail.maksimus40a.test.stand.features.base.interfaces.BaseRepository;
import com.gmail.maksimus40a.test.stand.features.base.interfaces.BaseService;
import com.gmail.maksimus40a.test.stand.features.base.exeptions.NoSuchSearchCriteriaException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractService<T extends IdEntity> implements BaseService<T> {

    private final String LIMIT = "limit";

    private BaseRepository<T> repository;
    private List<String> entityFields;

    public AbstractService(BaseRepository<T> repository,
                           List<String> entityFields) {
        this.repository = repository;
        this.entityFields = entityFields;
    }

    @Override
    public List<T> getAllEntities() {
        return repository.getAllEntities();
    }

    @Override
    public List<T> getEntitiesByCriteria(Map<String, String> params) {
        return repository.getEntitiesByCriteria(getSearchCriteria(params), getLimit(params));
    }

    private String getSearchCriteria(Map<String, String> params) {
        return params.get(getSearchFieldName(params));
    }

    private String getSearchFieldName(Map<String, String> params) {
        return entityFields.stream()
                .filter(isPresentSearchedField(params))
                .findFirst()
                .orElseThrow(() -> new NoSuchSearchCriteriaException("There is no entity by the required criterion."));
    }

    private Predicate<String> isPresentSearchedField(Map<String, String> params) {
        return fieldName -> params.keySet()
                .stream()
                .anyMatch(field -> field.equals(fieldName));
    }

    private int getLimit(Map<String, String> requestParams) {
        return Integer.parseInt(requestParams.getOrDefault(LIMIT, String.valueOf(Integer.MAX_VALUE)));
    }

    @Override
    public Optional<T> getEntityById(Integer id) {
        return repository.getEntityById(id);
    }

    @Override
    public T addEntity(T entity) {
        return repository.addEntity(entity);
    }

    @Override
    public Optional<T> editEntityById(Integer id, T entity) {
        return repository.editEntity(id, entity);
    }

    @Override
    public Optional<T> deleteEntityById(Integer id) {
        return repository.deleteEntityById(id);
    }
}