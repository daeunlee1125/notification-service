package kr.co.daeun.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldValidationErrorDTO {
    private String field;
    private String message;
}