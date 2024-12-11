package com.popcorn.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Continent {
    ASIA("Asia"),
    AFRICA("Africa"),
    AUSTRALIA("Australia"),
    ANTARCTICA("Antarctica"),
    EUROPE("Europe"),
    NORTH_AMERICA("North America"),
    SOUTH_AMERICA("South America");

    private final String value;
}
