package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkBuilder implements LinkBuilder<UserDto>{

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String GO_TO_ME = "goToMe";
    private static final String GO_TO_ORDERS = "goToOrders";

    @Override
    public LinkModel<List<LinkModel<UserDto>>> buildForAll(int page, int size, long pageAmount, List<UserDto> dtoList) {
        List<LinkModel<UserDto>> linkModelList =
                dtoList.stream()
                        .map(userDto -> {
                            LinkModel<UserDto> userDtoLinkModel = new LinkModel<>(userDto);
                            userDtoLinkModel.
                                    add(linkTo(methodOn(UserController.class)
                                                    .findById(userDto.getId())).withRel(GO_TO_ME),
                                            linkTo(methodOn(UserController.class)
                                                    .findOrders(userDto.getId(),
                                                            DEFAULT_PAGE,
                                                            DEFAULT_PAGE_SIZE)).withRel(GO_TO_ORDERS));
                            return userDtoLinkModel;
                        })
                        .collect(Collectors.toList());
        return new LinkModel<>(linkModelList);
    }

    @Override
    public LinkModel<UserDto> buildForOne(UserDto dto) {
        LinkModel<UserDto> linkModel = new LinkModel<>(dto);
        linkModel.add(
                linkTo(methodOn(UserController.class)
                        .findOrders(dto.getId(),
                                DEFAULT_PAGE,
                                DEFAULT_PAGE_SIZE)).withRel(GO_TO_ORDERS)
        );
        return linkModel;
    }

}
