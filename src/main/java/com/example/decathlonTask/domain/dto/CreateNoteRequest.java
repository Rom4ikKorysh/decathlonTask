package com.example.decathlonTask.domain.dto;

import java.time.LocalDateTime;

public class CreateNoteRequest {

	private String title;

	private String content;

	private LocalDateTime lastModificationDate;


	public CreateNoteRequest() {
	}

	public CreateNoteRequest(String title, String content) {
		this.title = title;
		this.content = content;
		this.lastModificationDate = LocalDateTime.now();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(LocalDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

}
