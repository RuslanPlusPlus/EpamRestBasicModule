package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.LinkBuilder;
import com.epam.esm.hateoas.LinkModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";

    private final OrderService orderService;
    private final LinkBuilder<OrderDto> linkBuilder;
    private final PaginationValidator paginationValidator;

    @Autowired
    public OrderController(OrderService orderService,
                           LinkBuilder<OrderDto> linkBuilder,
                           PaginationValidator paginationValidator){
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
        this.paginationValidator = paginationValidator;
    }

    @GetMapping
    public HttpEntity<LinkModel<List<LinkModel<OrderDto>>>> findAll(
            @RequestParam(name = "sort", required = false) List<String> sortParams,
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){

        long pageAmount = orderService.countPages(size);
        paginationValidator.checkIfPageExists(page, orderService.countPages(size));
        return new ResponseEntity<>(
                linkBuilder.buildForAll(page, size, pageAmount, orderService.findAll(page, size, sortParams)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public HttpEntity<LinkModel<OrderDto>> findById(@PathVariable Long id){
        // TODO: 18.10.2021 check role and id equality
        return new ResponseEntity<>(
                linkBuilder.buildForOne(orderService.findById(id)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public HttpEntity<LinkModel<OrderDto>> save(@RequestBody OrderDto orderDto){
        return new ResponseEntity<>(
                linkBuilder.buildForOne(orderService.save(orderDto)),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpEntity<LinkModel<OrderDto>> delete(@PathVariable Long id) {
        orderService.delete(id);
        return null;
    }
}
