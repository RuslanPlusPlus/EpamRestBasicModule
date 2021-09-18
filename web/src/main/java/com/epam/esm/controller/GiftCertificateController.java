package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.CertificateLinkBuilder;
import com.epam.esm.hateoas.LinkBuilder;
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

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService){
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public HttpEntity<LinkBuilder<List<LinkBuilder<GiftCertificateDto>>>> findAll(@RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){
        long pagesAmount = giftCertificateService.countPages(size);
        if (page > pagesAmount){
            // TODO: 18.09.2021 throw exception
        }
        return new ResponseEntity<>(
                CertificateLinkBuilder.buildForAll(
                        page, size, pagesAmount, giftCertificateService.findAll(page, size)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable long id) {
        return giftCertificateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto){
        return giftCertificateService.save(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto update(
            @RequestBody GiftCertificateDto updatedCertificateDto,
            @PathVariable long id){

        return giftCertificateService.update(updatedCertificateDto, id);
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
