package com.gmail.maksimus40a.test.stand.features.base.abstraction;

import com.gmail.maksimus40a.test.stand.features.base.IdEntity;
import com.gmail.maksimus40a.test.stand.features.base.interfaces.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractInMemoryRepository<T extends IdEntity> implements BaseRepository<T> {

    protected Map<Integer, T> entityMap = new ConcurrentHashMap<>();

    private Function<String, Predicate<T>> searchFunction;
    private AtomicInteger nextIdGenerator = new AtomicInteger(1);

    public AbstractInMemoryRepository(Function<String, Predicate<T>> searchFunction) {
        this.searchFunction = searchFunction;
    }

    public abstract Optional<T> editEntity(Integer id, T editedEntity);

    @Override
    public int countOfEntities() {
        return this.entityMap.size();
    }

    @Override
    public List<T> getAllEntities() {
        return new ArrayList<>(entityMap.values());
    }

    @Override
    public Optional<T> getEntityById(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) return Optional.empty();
        return Optional.ofNullable(entityMap.get(id));
    }

    @Override
    public List<T> getEntitiesByCriteria(String criteria, long limit) {
        return entityMap.values()
                .stream()
                .filter(searchFunction.apply(criteria))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public T addEntity(T entity) {
        Integer id = nextId();
        entity.setId(id);
        entityMap.put(id, entity);
        return entity;
    }

    public Optional<T> deleteEntityById(Integer id) {
        if (id < 0) throw new IllegalArgumentException("Id must be greater than 0. Your id = " + id);
        if (id > this.countOfEntities()) Optional.empty();
        T removed = entityMap.remove(id);
        return Optional.ofNullable(removed);
    }

    private Integer nextId() {
        return nextIdGenerator.getAndIncrement();
    }
}
