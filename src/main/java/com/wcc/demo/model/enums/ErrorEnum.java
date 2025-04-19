package com.wcc.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {

    /*** User Error Codes ***/
    INVALID_USERNAME("9001", "Invalid Username"),
    INVALID_PASSWORD("9002", "Invalid Password"),
    USER_NOT_FOUND("9003", "User not found"),
    USER_ALREADY_EXISTS("9004", "User already exists"),
    USERNAME_IS_REQUIRED("9005", "Username is required"),
    PASSWORD_IS_REQUIRED("9006", "Password is required"),

    /*** Postcode Error Codes ***/
    INVALID_POSTCODE("8001", "Invalid Postcode"),
    POSTCODE_NOT_FOUND("8002", "Postcode not found"),
    INVALID_DISTANCE("8003", "Invalid Distance"),
    POSTCODE_IS_REQUIRED("8004", "Postcode is required"),

    /*** General Error Codes ***/
    UNAUTHORIZED("401", "Unauthorized"),
    INVALID_USERNAME_OR_PASSWORD("402", "Invalid username or password"),
    REQUIRED_HEADERS_MISSING("403", "Required headers are missing: x-username and x-password"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    BAD_REQUEST("400", "Bad Request");

    private final String code;
    private final String message;
}