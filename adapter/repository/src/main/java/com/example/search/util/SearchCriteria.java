package com.example.search.util;

public class SearchCriteria {
    public enum Operation {
        EQUAL, LIKE , LESS_THAN , ARRAY_ANY, GRETER_THAN
    }

    private String key;
    private Operation operation;
    private Object value;


    public SearchCriteria(String key, Operation operarion,Object value) {
        this.key = key;
        this.value = value;
        this.operation = operarion;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Operation getOperation() {
        return this.operation;
    }
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    public Object getValue() {
        return this.value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public static Operation getSimpleOperation(final char input) {
        switch (input) {
            case '=':
                return Operation.EQUAL;

            case '>':
                return Operation.GRETER_THAN;
            case '<':
                return Operation.LESS_THAN;
            case '~':
                return Operation.LIKE;
            case '^':
                return Operation.ARRAY_ANY;
            default:
                return null;
        }
    }
}