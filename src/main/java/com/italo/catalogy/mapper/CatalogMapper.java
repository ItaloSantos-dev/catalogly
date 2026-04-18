package com.italo.catalogy.mapper;

import com.italo.catalogy.dto.catalog.CatalogPublicResponseDTO;
import com.italo.catalogy.model.CatalogModel;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapper {
    public CatalogPublicResponseDTO modelToPublicResponse(CatalogModel catalogModel){
        return new CatalogPublicResponseDTO(
                catalogModel.getSeller().getUser().getFirstName(),
                catalogModel.getName(),
                catalogModel.getSlug(),
                catalogModel.getSlogan(),
                catalogModel.getAbout(),
                catalogModel.getFisicAddress(),
                catalogModel.getPhone(),
                catalogModel.getImageIconPath(),
                catalogModel.getImageBannerPath()
        );
    }
}
