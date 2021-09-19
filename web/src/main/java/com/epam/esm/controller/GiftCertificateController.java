package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.LinkBuilder;
import com.epam.esm.hateoas.LinkModel;
import com.epam.esm.service.GiftCertificateService;
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

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     LinkBuilder<GiftCertificateDto> linkBuilder){
        this.giftCertificateService = giftCertificateService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    public HttpEntity<LinkModel<List<LinkModel<GiftCertificateDto>>>> findAll(
            @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){
        long pagesAmount = giftCertificateService.countPages(size);
        if (page > pagesAmount){
            // TODO: 18.09.2021 throw exception
        }
        return new ResponseEntity<>(
                linkBuilder.buildForAll(
                        page, size, pagesAmount, giftCertificateService.findAll(page, size)
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




    @GetMapping("/param")
    public List<GiftCertificateDto> findByTagName(@RequestParam(required = false, name = "tagName")String tagName,
                                                  @RequestParam(required = false, name = "partSearch") String partSearch,
                                                  @RequestParam(required = false, name = "sortByName")String sortByName,
                                                  @RequestParam(required = false, name = "sortByCreateDate")String sortByCreateDate
                                                  ){
        return giftCertificateService.findByQueryParams(tagName, partSearch, sortByName, sortByCreateDate);
    }
}
