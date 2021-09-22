package com.epam.esm.sort;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SortParser<T extends Enum<T>>{
    //request format: GET /path?sort=param1:asc,param2:desc
    private static final String PARAM_SORT_TYPE_DELIMITER = ":";
    private static final int PAIR_SIZE = 2;
    private static final String SPACE_SYMBOL = " ";
    private static final String EMPTY_LINE = "";

    public List<SortParameter<T>> parseSortParams(List<String> sortParams, Class<T> enumClass){
        List<SortParameter<T>> sortParameterList = new ArrayList<>();
        if (sortParams != null){
            sortParams.forEach(
                    param -> {
                        String[] paramTypePair = param.split(PARAM_SORT_TYPE_DELIMITER);
                        if (paramTypePair.length != PAIR_SIZE){
                            return;
                        }
                        String sortParameter = paramTypePair[0].toUpperCase().replaceAll(SPACE_SYMBOL, EMPTY_LINE);
                        String sortType = paramTypePair[1].toUpperCase().replaceAll(SPACE_SYMBOL, EMPTY_LINE);
                        List<String> enumSortParams = Stream.of(enumClass.getEnumConstants())
                                .map(Enum::name)
                                .collect(Collectors.toList());
                        List<String> enumSortTypes = Stream.of(SortType.values())
                                .map(Enum::name)
                                .collect(Collectors.toList());
                        if (!enumSortParams.contains(sortParameter) || !enumSortTypes.contains(sortType)){
                            return;
                        }
                        SortParameter<T> sortParamObject = new SortParameter<>(
                                SortType.valueOf(sortType),
                                T.valueOf(enumClass, sortParameter)
                        );
                        sortParameterList.add(sortParamObject);
                    }
            );
        }
        return sortParameterList;
    }
}
