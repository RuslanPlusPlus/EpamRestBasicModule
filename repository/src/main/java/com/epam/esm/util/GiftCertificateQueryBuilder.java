package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateQueryBuilder {

    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String ID_PARAM = "id";
    private static final String TAGS_ATTRIBUTE = "tags";

    public void buildQuery(GiftCertificateFilterCriteria filterCriteria,
                           CriteriaBuilder criteriaBuilder,
                           CriteriaQuery<GiftCertificate> criteriaQuery,
                           Root<GiftCertificate> root){
        List<Predicate> predicates = new ArrayList<>();
        String partNameOrDescription = filterCriteria.getFilterCriteria();

        if (partNameOrDescription != null){
            Predicate namePredicate = criteriaBuilder.like(root.get(NAME_PARAM), "%" + partNameOrDescription + "%");
            Predicate descriptionPredicate = criteriaBuilder.like(root.get(DESCRIPTION_PARAM), "%" + partNameOrDescription + "%");
            Predicate finalPredicate = criteriaBuilder.or(namePredicate, descriptionPredicate);
            predicates.add(finalPredicate);
        }
        List<String> tagNames = filterCriteria.getTagNames();
        if (tagNames != null){
            Join<GiftCertificate, Tag> tags = root.join(TAGS_ATTRIBUTE);
            CriteriaBuilder.In<String> tagNamesClause = criteriaBuilder.in(tags.get(NAME_PARAM));
            tagNames.forEach(tagNamesClause::value);
            predicates.add(tagNamesClause);

            // TODO: 20.09.2021 think about
            criteriaQuery.groupBy(root.get(ID_PARAM));
            criteriaQuery.having(
                    criteriaBuilder.equal(
                            criteriaBuilder.count(root.get(ID_PARAM)), tagNames.size()));

        }

        Predicate [] arrayPredicate = new Predicate[predicates.size()];
        predicates.toArray(arrayPredicate);
        criteriaQuery.where(arrayPredicate);
    }
}
