package com.example.notepad.handlers;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ExceptionValues(String message,
							  HttpStatus httpStatus,
							  ZonedDateTime timestamp) {
}