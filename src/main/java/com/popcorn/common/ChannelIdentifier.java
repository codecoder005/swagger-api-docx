package com.popcorn.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChannelIdentifier {
    WEB("Web"),
    MOBILE("Mobile"),
    IOT_DEVICE("IOT-Device"),
    DESKTOP("Desktop");
    private final String value;
}
