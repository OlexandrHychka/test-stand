package com.gmail.maksimus40a.test.stand.bases;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class SearchRepository<T> implements BaseRepository<T> {

    private Function<String, Predicate<T>> searchFunction;
    private AtomicInteger nextIdGenerator = new AtomicInteger(1);

    protected Map<Integer, T> entityMap = new ConcurrentHashMap<>();

    public SearchRepository(Function<String, Predicate<T>> searchFunction) {
        this.searchFunction = searchFunction;
    }

    public List<T> getEntitiesByCriteria(String criteria, long limit) {
        return entityMap.values()
                .stream()
                .filter(searchFunction.apply(criteria))
                .limit(limit)
                .collect(Collectors.toList());
    }

    protected Integer nextId() {
        return nextIdGenerator.getAndIncrement();
    }

    protected void reduceId() {
        nextIdGenerator.decrementAndGet();
    }
}
