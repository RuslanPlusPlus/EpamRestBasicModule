package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.LinkBuilder;
import com.epam.esm.hateoas.LinkModel;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private final TagService tagService;
    private final LinkBuilder<TagDto> linkBuilder;

    @Autowired
    public TagController(TagService tagService,
                         LinkBuilder<TagDto> linkBuilder){
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    public HttpEntity<LinkModel<List<LinkModel<TagDto>>>> findAll(@RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){
        long pagesAmount = tagService.countPages(size);
        if (page > pagesAmount){
            // TODO: 18.09.2021 throw exception
        }

        return new ResponseEntity<>(
                linkBuilder.buildForAll(page, size, pagesAmount, tagService.findAll(page, size)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public HttpEntity<LinkModel<TagDto>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(
                linkBuilder.buildForOne(tagService.findById(id)),
                HttpStatus.OK
        );
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<LinkModel<TagDto>> add(@RequestBody TagDto tagDto) {
        return new ResponseEntity<>(
                linkBuilder.buildForOne(tagService.save(tagDto)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TagDto delete(@PathVariable Long id) {
        tagService.delete(id);
        return null;
    }
}
