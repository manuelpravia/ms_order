package org.mpravia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String names;
    private String surNames;
    private String documentType;
    private String documentNumber;
}
