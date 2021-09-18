package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> findAll(@RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page,
                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size){
        long pagesAmount = tagService.countPages(size);
        if (page > pagesAmount){
            // TODO: 18.09.2021 throw exception
        }

        return tagService.findAll(page, size);
    }


    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto add(@RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }
}
