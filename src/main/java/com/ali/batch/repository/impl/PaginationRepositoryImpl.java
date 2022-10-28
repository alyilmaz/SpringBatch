package com.ali.batch.repository.impl;

import com.ali.batch.domain.client.Client;
import com.ali.batch.domain.client.ClientPage;
import com.ali.batch.domain.client.ClientSearchCriteria;
import com.ali.batch.repository.PaginationRepository;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginationRepositoryImpl implements PaginationRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PaginationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Client> getAllBySearch(ClientPage clientPage, ClientSearchCriteria clientSearchCriteria) {
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        List<Predicate> predicates = new ArrayList<>();


        Predicate predicate = getPredicate(clientSearchCriteria, clientRoot);
        criteriaQuery.where(predicate);
        setOrder(clientPage,criteriaQuery,clientRoot);


        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(clientPage.getPageNumber() * clientPage.getPageSize());
        typedQuery.setMaxResults(clientPage.getPageSize());

        Pageable pageable = getPageable(clientPage);

        long clientCount = getClientCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, clientCount);
    }

    private long getClientCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Client> countRoot = countQuery.from(Client.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(ClientPage clientPage) {
        Sort sort = Sort.by(clientPage.getSortDirection(), clientPage.getSortBy());
        return PageRequest.of(clientPage.getPageNumber(),clientPage.getPageSize(), sort);
    }

    private Predicate getPredicate(ClientSearchCriteria clientSearchCriteria, Root<Client> clientRoot){
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(clientSearchCriteria.getSearchName())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(clientRoot.get("name")),
                            criteriaBuilder.lower(criteriaBuilder.literal("%" + clientSearchCriteria.getSearchName() + "%")))
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ClientPage clientPage, CriteriaQuery<Client> clientCriteriaQuery, Root<Client> clientRoot){
        if(clientPage.getSortDirection().equals(Sort.Direction.ASC)){
            clientCriteriaQuery.orderBy(criteriaBuilder.asc(clientRoot.get(clientPage.getSortBy())));
        } else{
            clientCriteriaQuery.orderBy(criteriaBuilder.desc(clientRoot.get(clientPage.getSortBy())));
        }
    }
}
