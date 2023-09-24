package com.example.decathlonTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(AlreadyExistsException.class)
	ResponseEntity<Object> handleAlreadyExistException(AlreadyExistsException alreadyExistsException) {
		HttpStatus alreadyExist = HttpStatus.BAD_REQUEST;
		ExceptionValues alreadyExistExp = new ExceptionValues(
				alreadyExistsException.getMessage(),
				alreadyExist,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		return new ResponseEntity<>(alreadyExistExp, alreadyExist);
	}

	@ExceptionHandler(NotFoundException.class)
	ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
		HttpStatus notFound = HttpStatus.NOT_FOUND;
		ExceptionValues notFoundExp = new ExceptionValues(
				notFoundException.getMessage(),
				notFound,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		return new ResponseEntity<>(notFoundExp, notFound);
	}

}
