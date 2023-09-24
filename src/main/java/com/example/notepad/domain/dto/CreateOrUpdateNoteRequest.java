package com.example.notepad.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrUpdateNoteRequest {
	@NotBlank
	private String title;

	@NotNull
	private String content;

	public CreateOrUpdateNoteRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
