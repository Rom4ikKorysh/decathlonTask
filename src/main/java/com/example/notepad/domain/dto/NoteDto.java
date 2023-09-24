package com.example.notepad.domain.dto;

import com.example.notepad.domain.entity.Note;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class NoteDto {
	private Long id;

	private String title;

	private String content;

	private String dateTime;

	public NoteDto(Long id, String title, String content, String dateTime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
	}

	public static NoteDto noteToNoteDto(Note note) {
		return new NoteDto(
				note.getId(),
				note.getTitle(),
				note.getContent(),
				note.getLastModificationDate().format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
