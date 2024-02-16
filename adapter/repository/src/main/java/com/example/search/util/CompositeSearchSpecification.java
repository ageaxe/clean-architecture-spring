package com.example.search.util;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
public class CompositeSearchSpecification<T> extends BaseSpecification implements Specification<T> {

    private static final long serialVersionUID = -3555284026042846014L;

    private final SearchSpecification<T> searchSpecificationRight;
    private final SearchSpecification<T> searchSpecificationLeft;

    private JoinCondiion joinCondition;



    public CompositeSearchSpecification(SearchCriteria[] searchCriteriaLeft, SearchCriteria[] searchCriteriaRight,
                                        JoinCondiion joinConditionLeft, JoinCondiion joinConditionRight,
                                        JoinCondiion joinConditionBetweenLeftRight) {
        if (searchCriteriaLeft == null || searchCriteriaRight == null) {
            throw new IllegalArgumentException("Specify search Criteria");
        }

        this.joinCondition = joinConditionBetweenLeftRight;
        this.searchSpecificationRight = new SearchSpecification<T>(searchCriteriaRight,joinConditionRight);
        this.searchSpecificationLeft = new SearchSpecification<T>(searchCriteriaLeft,joinConditionLeft);

    }

    public CompositeSearchSpecification(SearchSpecification<T> searchSpecificationLeft,
                                        SearchSpecification<T> searchSpecificationRight,
                                        JoinCondiion joinConditionBetweenLeftRight) {
        if (searchSpecificationLeft == null || searchSpecificationRight == null) {
            throw new IllegalArgumentException("Sepcify search Criteria");
        }

        this.joinCondition = joinConditionBetweenLeftRight;
        this.searchSpecificationRight = searchSpecificationRight;
        this.searchSpecificationLeft = searchSpecificationLeft;

    }


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (JoinCondiion.AND == this.joinCondition)
            return builder.and(this.searchSpecificationLeft.toPredicate(root, query, builder),this.searchSpecificationRight.toPredicate(root, query, builder));
        else
            return builder.or(this.searchSpecificationLeft.toPredicate(root, query, builder),this.searchSpecificationRight.toPredicate(root, query, builder));

    }



}