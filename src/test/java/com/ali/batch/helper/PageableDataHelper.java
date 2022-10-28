package com.ali.batch.helper;

import com.ali.batch.domain.client.ClientPage;
import org.springframework.data.domain.*;

import javax.persistence.*;
import java.util.*;

public abstract class PageableDataHelper<S> {

    public Pageable getPageable(Integer pageNo, Integer pageSize, String sortDirection, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                "asc".equalsIgnoreCase(sortDirection) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        return pageable;
    }

    public ClientPage getClientPage(Integer pageNo, Integer pageSize, String sortDir, String sortBy){
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(sortDir)){
            sortDirection = Sort.Direction.DESC;
        }
        ClientPage clientPage = new ClientPage(pageNo, pageSize, sortDirection, sortBy);
        return clientPage;
    }

    public Page<S> getPageData(Pageable pageable, S getData) {
        TypedQuery<S> typedQuery = new TypedQuery<S>() {
            @Override
            public List<S> getResultList() {
                return Arrays.asList(getData);
            }

            @Override
            public S getSingleResult() {
                return getData;
            }

            @Override
            public TypedQuery<S> setMaxResults(int maxResult) {
                return null;
            }

            @Override
            public TypedQuery<S> setFirstResult(int startPosition) {
                return null;
            }

            @Override
            public TypedQuery<S> setHint(String hintName, Object value) {
                return null;
            }

            @Override
            public <T> TypedQuery<S> setParameter(Parameter<T> param, T value) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(Parameter<Calendar> param, Calendar value,
                                              TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(String name, Object value) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(String name, Calendar value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(String name, Date value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(int position, Object value) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(int position, Calendar value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<S> setParameter(int position, Date value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<S> setFlushMode(FlushModeType flushMode) {
                return null;
            }

            @Override
            public TypedQuery<S> setLockMode(LockModeType lockMode) {
                return null;
            }

            @Override
            public int executeUpdate() {
                return 0;
            }

            @Override
            public int getMaxResults() {
                return 0;
            }

            @Override
            public int getFirstResult() {
                return 0;
            }

            @Override
            public Map<String, Object> getHints() {
                return null;
            }

            @Override
            public Set<Parameter<?>> getParameters() {
                return null;
            }

            @Override
            public Parameter<?> getParameter(String name) {
                return null;
            }

            @Override
            public <S> Parameter<S> getParameter(String name, Class<S> type) {
                return null;
            }

            @Override
            public Parameter<?> getParameter(int position) {
                return null;
            }

            @Override
            public <S> Parameter<S> getParameter(int position, Class<S> type) {
                return null;
            }

            @Override
            public boolean isBound(Parameter<?> param) {
                return false;
            }

            @Override
            public <T> T getParameterValue(Parameter<T> param) {
                return null;
            }

            @Override
            public Object getParameterValue(String name) {
                return null;
            }

            @Override
            public Object getParameterValue(int position) {
                return null;
            }

            @Override
            public FlushModeType getFlushMode() {
                return null;
            }

            @Override
            public LockModeType getLockMode() {
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> cls) {
                return null;
            }

        };
        Page<S> pageDoc = new PageImpl<>(typedQuery.getResultList(), pageable, 1);
        return pageDoc;
    }
}
