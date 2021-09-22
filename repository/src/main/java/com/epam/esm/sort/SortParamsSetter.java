package com.epam.esm.sort;

import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SortParamsSetter <R, T extends Enum<T>>{
    public void setSortParams(CriteriaBuilder criteriaBuilder,
                              CriteriaQuery<R> criteriaQuery,
                              Root<R> root,
                              List<SortParameter<T>> sortParameterList){
        List<Order> orderList = new ArrayList<>();
        sortParameterList.forEach(sortParameter -> {
            switch (sortParameter.getSortType()){
                case ASC ->{
                    Order order = criteriaBuilder.asc(root.get(sortParameter.getSortParamClass().toString()));
                    orderList.add(order);
                }

                case DESC ->{
                    Order order = criteriaBuilder.desc(root.get(sortParameter.getSortParamClass().toString()));
                    orderList.add(order);
                }
            }
            Order[] orderArray = new Order[orderList.size()];
            orderList.toArray(orderArray);
            criteriaQuery.orderBy(orderArray);
        });
    }
}
