package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService){
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(){
        return giftCertificateService.findAll();
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
    public GiftCertificateDto update(@RequestBody GiftCertificateDto updatedCertificateDto, @PathVariable long id){
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
