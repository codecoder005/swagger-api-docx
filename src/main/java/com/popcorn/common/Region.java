package com.popcorn.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
    US_EAST_1("us-east-1"),
    US_WEST_1("us-west-1"),

    EU_EAST_1("eu-east-1"),
    EU_WEST_1("eu-west-1"),

    AP_EAST_1("ap-east-1"),
    AP_WEST_1("ap-west-1"),

    AP_SOUTH_1("ap-south-1"),

    AP_SOUTHEAST_1("ap-southeast-1");

    private final String value;
}
