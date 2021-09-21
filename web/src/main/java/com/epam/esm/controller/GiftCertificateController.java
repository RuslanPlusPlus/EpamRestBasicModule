package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.LinkBuilder;
import com.epam.esm.hateoas.LinkModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateFilterCriteria;
import com.epam.esm.validator.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";

    private final GiftCertificateService giftCertificateService;
    private final LinkBuilder<GiftCertificateDto> linkBuilder;
    private final PaginationValidator paginationValidator;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     LinkBuilder<GiftCertificateDto> linkBuilder,
                                     PaginationValidator paginationValidator){
        this.giftCertificateService = giftCertificateService;
        this.linkBuilder = linkBuilder;
        this.paginationValidator = paginationValidator;
    }

    @GetMapping
    public HttpEntity<LinkModel<List<LinkModel<GiftCertificateDto>>>> findAll(
            @RequestParam(name = "tagName", required = false) List<String> tagNames,
            @RequestParam(name = "partNameOrDescription", required = false) String filterCriteria,
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){
        long pagesAmount = giftCertificateService.countPages(size);
        paginationValidator.checkIfPageExists(page, pagesAmount);
        return new ResponseEntity<>(
                linkBuilder.buildForAll(
                        page,
                        size,
                        pagesAmount,
                        giftCertificateService.findAll(new GiftCertificateFilterCriteria(filterCriteria, tagNames), page, size)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public HttpEntity<LinkModel<GiftCertificateDto>> findById(@PathVariable long id) {
        return new ResponseEntity<>(
                linkBuilder.buildForOne(giftCertificateService.findById(id)),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<LinkModel<GiftCertificateDto>> save(@RequestBody GiftCertificateDto giftCertificateDto){
        return new ResponseEntity<>(
                linkBuilder.buildForOne(giftCertificateService.save(giftCertificateDto)),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GiftCertificateDto delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
        return null;
    }

    @PatchMapping("/{id}")
    public HttpEntity<LinkModel<GiftCertificateDto>> update(
            @RequestBody GiftCertificateDto updatedCertificateDto,
            @PathVariable long id){

        return new ResponseEntity<>(
                linkBuilder.buildForOne(giftCertificateService.update(updatedCertificateDto, id)),
                HttpStatus.OK
                );
    }

}
