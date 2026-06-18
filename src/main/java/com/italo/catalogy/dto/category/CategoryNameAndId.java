package com.italo.catalogy.dto.category;

import java.util.UUID;

public record CategoryNameAndId (
    UUID id,
    String name
){
}
