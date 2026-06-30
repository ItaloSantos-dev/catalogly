package com.italo.catalogy.dto.item;

import org.springframework.web.multipart.MultipartFile;

public record UpdateImageItem(
    MultipartFile image,
    boolean update
) {
    

}
