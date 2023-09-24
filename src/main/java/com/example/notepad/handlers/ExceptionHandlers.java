package com.example.notepad.handlers;

import com.example.notepad.exception.AlreadyExistsException;
import com.example.notepad.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(AlreadyExistsException.class)
	ResponseEntity<Object> handleAlreadyExistException(AlreadyExistsException alreadyExistsException) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ExceptionValues alreadyExistExp = new ExceptionValues(
				alreadyExistsException.getMessage(),
				httpStatus,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		return new ResponseEntity<>(alreadyExistExp, httpStatus);
	}

	@ExceptionHandler(NotFoundException.class)
	ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ExceptionValues notFoundExp = new ExceptionValues(
				notFoundException.getMessage(),
				httpStatus,
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		return new ResponseEntity<>(notFoundExp, httpStatus);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> fieldError.getDefaultMessage())
				.toList();
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}

}
