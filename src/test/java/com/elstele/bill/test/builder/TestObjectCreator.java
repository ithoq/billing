package com.elstele.bill.test.builder;

/**
 * Created by ivan on 15/11/30.
 */
public interface TestObjectCreator<T,K> {

    public T build();
    public K getRes();

}
