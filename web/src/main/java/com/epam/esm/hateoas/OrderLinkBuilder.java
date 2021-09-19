package com.epam.esm.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkBuilder implements LinkBuilder<OrderDto>{

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String GO_TO_ME = "goToMe";
    private static final String DELETE_ME = "deleteMe";
    private static final String GO_TO_CUSTOMER = "goToCustomer";

    @Override
    public LinkModel<List<LinkModel<OrderDto>>> buildForAll(int page, int size, long pageAmount, List<OrderDto> dtoList) {
        List<LinkModel<OrderDto>> linkModelList =
                dtoList.stream()
                        .map(orderDto -> {
                            LinkModel<OrderDto> orderDtoLinkModel = new LinkModel<>(orderDto);
                            orderDtoLinkModel.
                                    add(linkTo(methodOn(OrderController.class)
                                                    .findById(orderDto.getId())).withRel(GO_TO_ME),
                                            linkTo(methodOn(OrderController.class)
                                                    .delete(orderDto.getId())).withRel(DELETE_ME),
                                            linkTo(methodOn(UserController.class)
                                                    .findById(orderDto.getId())).withRel(GO_TO_CUSTOMER));
                            return orderDtoLinkModel;
                        })
                        .collect(Collectors.toList());
        // TODO: 19.09.2021 page transfer feature
        return new LinkModel<>(linkModelList);
    }

    @Override
    public LinkModel<OrderDto> buildForOne(OrderDto dto) {
        LinkModel<OrderDto> linkModel = new LinkModel<>(dto);
        linkModel.add(
                linkTo(methodOn(OrderController.class)
                        .delete(dto.getId())).withRel(DELETE_ME)
        );
        return linkModel;
    }
}
