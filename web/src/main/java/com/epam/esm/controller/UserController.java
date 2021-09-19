package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.LinkModel;
import com.epam.esm.hateoas.OrderLinkBuilder;
import com.epam.esm.hateoas.UserLinkBuilder;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";

    private final UserService userService;
    private final UserLinkBuilder userLinkBuilder;
    private final OrderLinkBuilder orderLinkBuilder;

    @Autowired
    public UserController(UserService userService,
                          UserLinkBuilder userLinkBuilder,
                          OrderLinkBuilder orderLinkBuilder){
        this.userService = userService;
        this.userLinkBuilder = userLinkBuilder;
        this.orderLinkBuilder = orderLinkBuilder;
    }

    @GetMapping
    public HttpEntity<LinkModel<List<LinkModel<UserDto>>>> findAll(
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){

        long pageAmount = userService.countPages(size);
        if (page > pageAmount){
            // TODO: 19.09.2021 throw exception
        }

        return new ResponseEntity<>(
                userLinkBuilder.buildForAll(page, size, pageAmount,userService.findAll(page, size)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public HttpEntity<LinkModel<UserDto>> findById(@PathVariable Long id){
        return new ResponseEntity<>(
                userLinkBuilder.buildForOne(userService.findById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/orders/{id}")
    public HttpEntity<LinkModel<List<LinkModel<OrderDto>>>> findOrders(
            @PathVariable Long id,
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size) {

        long pageAmount = userService.countUserOrdersPages(id, size);
        if (page > pageAmount){
            // TODO: 19.09.2021 throw exception
        }

        return new ResponseEntity<>(
                orderLinkBuilder.buildForAll(page, size, pageAmount, userService.findUserOrders(id, page, size)),
                HttpStatus.OK
        );
    }
}
