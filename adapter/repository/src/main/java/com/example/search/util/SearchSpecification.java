package com.example.search.util;

import static java.util.stream.Collectors.joining;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification<T> extends BaseSpecification implements Specification<T>  {

    /**
     *
     */

    private static final long serialVersionUID = 2080662752265477389L;
    private final SearchCriteria[] criteria;


    private final JoinCondiion joinCondition;

    private static Logger LOGGER = LoggerFactory.getLogger(SearchSpecification.class);

    public SearchSpecification(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            throw new IllegalArgumentException("Specify search Criteria");
        }
        this.joinCondition = JoinCondiion.NONE;
        this.criteria = new SearchCriteria[] { searchCriteria };
    }

    public SearchSpecification(SearchCriteria[] searchCriteria, JoinCondiion condition) {
        if (searchCriteria == null) {
            throw new IllegalArgumentException("Specify search Criteria");
        } else if (searchCriteria.length == 1) {
            this.joinCondition = JoinCondiion.NONE;
        } else {
            this.joinCondition = condition;
        }
        this.criteria = searchCriteria;
    }


    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (JoinCondiion.NONE == this.joinCondition) {
            return this.toPredicate(criteria[0], root, query, builder);
        } else {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (SearchCriteria criteria : criteria) {
                predicates.add(this.toPredicate(criteria, root, query, builder));
            }
            if (JoinCondiion.AND == this.joinCondition)
                return builder.and(predicates.toArray(new Predicate[0]));
            else
                return builder.or(predicates.toArray(new Predicate[0]));

        }

    }

    private Predicate toPredicate(SearchCriteria criteria, Root<T> root, CriteriaQuery<?> query,
                                  CriteriaBuilder builder) {
        if (root.get(criteria.getKey()).getJavaType() == Map.class) {
            if (criteria.getValue() instanceof String) {
                Expression<String> funParam = builder.literal(criteria.getValue().toString());
                return builder.isTrue(builder.function("exist", Boolean.class, root.get(criteria.getKey()), funParam));

            } else if (criteria.getValue() instanceof String[]) {
                String arrayString = Arrays.asList((String[])criteria.getValue()).stream().collect(joining(",", "", ""));
                Expression<String> arrayStringParam = builder.literal(arrayString);
                Expression<String> arraySplitParam = builder.literal(",");
                Expression<Array> sqlArrayParam = builder.function("string_to_array",java.sql.Array.class,arrayStringParam,arraySplitParam);
                return builder.isTrue(builder.function("exists_any",Boolean.class,root.get(criteria.getKey()),sqlArrayParam));
            } else {
                throw new UnsupportedOperationException();
            }
        }else if (root.get(criteria.getKey()).getJavaType() == String[].class && criteria.getOperation().equals(SearchCriteria.Operation.ARRAY_ANY)) {
            if (criteria.getValue() instanceof String) {
                Expression<String> funParam = builder.literal(criteria.getValue().toString());
                return builder.equal(funParam,builder.function("any", String.class, root.get(criteria.getKey())));
            } else {
                throw new UnsupportedOperationException();
            }
        } else if (criteria.getOperation().equals(SearchCriteria.Operation.GRETER_THAN)) {
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equals(SearchCriteria.Operation.LESS_THAN)) {
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equals(SearchCriteria.Operation.LIKE)) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equals(SearchCriteria.Operation.EQUAL)) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        LOGGER.warn("No Search Criteria Set for request , Matching Operation not found");
        return null;

    }


}