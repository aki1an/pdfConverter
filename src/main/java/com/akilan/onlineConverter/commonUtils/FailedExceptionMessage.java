package com.akilan.onlineConverter.commonUtils;

import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FailedExceptionMessage {
    private String message;
    private boolean retry;
}
