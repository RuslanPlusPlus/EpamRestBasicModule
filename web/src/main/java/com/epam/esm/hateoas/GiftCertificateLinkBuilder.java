package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLinkBuilder implements LinkBuilder<GiftCertificateDto>{
    private static final String GO_TO_ME = "goToMe";
    private static final String UPDATE_ME = "updateMe";
    private static final String DELETE_ME = "deleteMe";

    @Override
    public LinkModel<List<LinkModel<GiftCertificateDto>>> buildForAll(
            int page, int size, long pageAmount,
            List<GiftCertificateDto> giftCertificateDtoList)
    {
        List<LinkModel<GiftCertificateDto>> linkModelList =
                giftCertificateDtoList.stream()
                        .map(certificateDto -> {
                            LinkModel<GiftCertificateDto> certificateDtoLinkModel = new LinkModel<>(certificateDto);
                            certificateDtoLinkModel.
                                    add(linkTo(methodOn(GiftCertificateController.class)
                                            .findById(certificateDto.getId())).withRel(GO_TO_ME),
                                            linkTo(methodOn(GiftCertificateController.class)
                                            .update(certificateDto, certificateDto.getId())).withRel(UPDATE_ME),
                                            linkTo(methodOn(GiftCertificateController.class)
                                            .delete(certificateDto.getId())).withRel(DELETE_ME));
                                    return certificateDtoLinkModel;
                                })
                        .collect(Collectors.toList());
        return new LinkModel<>(linkModelList);
    }

    @Override
    public LinkModel<GiftCertificateDto> buildForOne(GiftCertificateDto giftCertificateDto){
        LinkModel<GiftCertificateDto> linkModel = new LinkModel<>(giftCertificateDto);
        linkModel.add(
                linkTo(methodOn(GiftCertificateController.class)
                        .update(giftCertificateDto, giftCertificateDto.getId())).withRel(UPDATE_ME),
                linkTo(methodOn(GiftCertificateController.class)
                        .delete(giftCertificateDto.getId())).withRel(DELETE_ME)
        );
        return linkModel;
    }

}
