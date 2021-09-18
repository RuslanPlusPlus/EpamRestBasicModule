package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CertificateLinkBuilder {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String GO_TO_ME = "goToMe";

    public static LinkBuilder<List<LinkBuilder<GiftCertificateDto>>> buildForAll(
            int page, int size, long pageAmount,
            List<GiftCertificateDto> giftCertificateDtoList)
    {
        List<LinkBuilder<GiftCertificateDto>> linkBuilderList =
                giftCertificateDtoList.stream()
                        .map(certificateDto -> {
                            LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder = new LinkBuilder<>(certificateDto);
                            certificateDtoLinkBuilder.
                                    add(linkTo(methodOn(GiftCertificateController.class)
                                            .findById(certificateDto.getId())).withRel(GO_TO_ME));
                                    return certificateDtoLinkBuilder;
                                })
                        .collect(Collectors.toList());
        return new LinkBuilder<>(linkBuilderList);
    }
}
