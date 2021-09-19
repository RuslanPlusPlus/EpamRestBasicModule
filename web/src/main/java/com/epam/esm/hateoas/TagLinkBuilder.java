package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder implements LinkBuilder<TagDto> {
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final String GO_TO_ME = "goToMe";
    private static final String DELETE_ME = "deleteMe";

    @Override
    public LinkModel<List<LinkModel<TagDto>>> buildForAll(int page, int size, long pageAmount, List<TagDto> tagDtoList) {
        {
            List<LinkModel<TagDto>> linkModelList =
                    tagDtoList.stream()
                            .map(tagDto -> {
                                LinkModel<TagDto> tagDtoLinkModel = new LinkModel<>(tagDto);
                                tagDtoLinkModel.
                                        add(linkTo(methodOn(TagController.class)
                                                        .findById(tagDto.getId())).withRel(GO_TO_ME),
                                                linkTo(methodOn(TagController.class)
                                                        .delete(tagDto.getId())).withRel(DELETE_ME));
                                return tagDtoLinkModel;
                            })
                            .collect(Collectors.toList());
            // TODO: 19.09.2021 page transfer feature
            return new LinkModel<>(linkModelList);
        }
    }

    @Override
    public LinkModel<TagDto> buildForOne(TagDto dto) {
        LinkModel<TagDto> linkModel = new LinkModel<>(dto);
        linkModel.add(
                linkTo(methodOn(TagController.class)
                        .delete(dto.getId())).withRel(DELETE_ME)
        );
        return linkModel;
    }
}
