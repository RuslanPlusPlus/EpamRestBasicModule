package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.LinkModel;
import com.epam.esm.hateoas.OrderLinkBuilder;
import com.epam.esm.hateoas.UserLinkBuilder;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.PaginationValidator;
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
    private final PaginationValidator paginationValidator;

    @Autowired
    public UserController(UserService userService,
                          UserLinkBuilder userLinkBuilder,
                          OrderLinkBuilder orderLinkBuilder,
                          PaginationValidator paginationValidator){
        this.userService = userService;
        this.userLinkBuilder = userLinkBuilder;
        this.orderLinkBuilder = orderLinkBuilder;
        this.paginationValidator = paginationValidator;
    }

    @GetMapping
    public HttpEntity<LinkModel<List<LinkModel<UserDto>>>> findAll(
            @RequestParam(name = "sort", required = false) List<String> sortParams,
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){

        long pageAmount = userService.countPages(size);
        paginationValidator.checkIfPageExists(page, pageAmount);
        return new ResponseEntity<>(
                userLinkBuilder.buildForAll(page, size, pageAmount,userService.findAll(page, size, sortParams)),
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
        paginationValidator.checkIfPageExists(page, pageAmount);
        return new ResponseEntity<>(
                orderLinkBuilder.buildForAll(page, size, pageAmount, userService.findUserOrders(id, page, size)),
                HttpStatus.OK
        );
    }
}
