package com.example.decathlonTask.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ExceptionValues(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {

}