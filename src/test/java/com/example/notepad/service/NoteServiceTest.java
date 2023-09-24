package com.example.notepad.service;

import com.example.notepad.domain.dto.CreateOrUpdateNoteRequest;
import com.example.notepad.domain.dto.NoteDto;
import com.example.notepad.domain.entity.Note;
import com.example.notepad.exception.AlreadyExistsException;
import com.example.notepad.exception.NotFoundException;
import com.example.notepad.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

	@Mock
	private NoteRepository noteRepository;
	private NoteService noteService;

	Long noteId = 1L;
	Note note;
	NoteDto noteDto;

	@BeforeEach
	void setUp() {
		noteService = new NoteService(noteRepository);
		note = new Note("test1", "test1");
		noteDto = NoteDto.noteToNoteDto(note);
	}

	@Test
	void findById() {
		when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

		Note returnedNote = noteService.findById(noteId);

		assertEquals(returnedNote, note);
	}

	@Test
	void findNoteByIdWhenNoteNotFound() {
		when(noteRepository.findById(noteId)).thenReturn(Optional.empty());
		Throwable noteNotFoundException = assertThrows(NotFoundException.class, () -> noteService.findById(noteId));

		assertEquals("Note with id " + noteId + " not found", noteNotFoundException.getMessage());
	}

	@Test
	void createNote() {
		CreateOrUpdateNoteRequest createNoteRequest = new CreateOrUpdateNoteRequest(note.getTitle(), note.getContent());

		when(noteRepository.findNoteByTitleIgnoreCase(note.getTitle())).thenReturn(Optional.empty());

		noteService.createNote(createNoteRequest);

		verify(noteRepository).save(any(Note.class));
	}

	@Test
	void createNoteWhenNoteTitleIsExists() {
		CreateOrUpdateNoteRequest createNoteRequest = new CreateOrUpdateNoteRequest(note.getTitle(), note.getContent());

		when(noteRepository.findNoteByTitleIgnoreCase(note.getTitle())).thenReturn(Optional.of(note));

		Exception alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> noteService.createNote(createNoteRequest));
		assertEquals("Note with this title " + note.getTitle() + " already exists", alreadyExistsException.getMessage());
	}

	@Test
	void deleteNoteById() {
		when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

		noteService.deleteNoteById(noteId);

		verify(noteRepository).delete(note);
	}

	@Test
	void updateNote() {
		when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

		CreateOrUpdateNoteRequest createNoteRequest = new CreateOrUpdateNoteRequest(note.getTitle(), note.getContent());

		noteService.updateNote(createNoteRequest, noteId);

		verify(noteRepository).save(any(Note.class));
	}

	@Test
	void updateNoteWhenIdIsNotExist() {
		when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

		CreateOrUpdateNoteRequest createNoteRequest = new CreateOrUpdateNoteRequest(note.getTitle(), note.getContent());

		Exception noteNotFoundException = assertThrows(NotFoundException.class, () -> noteService.updateNote(createNoteRequest, noteId));

		assertEquals("Note with id " + noteId + " not found", noteNotFoundException.getMessage());

		verify(noteRepository, times(0)).save(any(Note.class));
	}

	@Test
	void findNotesByTitleContaining() {
		List<Note> notes = List.of(note);

		when(noteRepository.findNotesByTitleContainingIgnoreCase("t")).thenReturn(notes);

		List<NoteDto> returnedNotes = noteService.findNotesByTitleContains("t");

		assertEquals(returnedNotes.size(), notes.size());
	}

	@Test
	void findAllNotes() {
		noteService.getAllNotes();

		verify(noteRepository).findAll();
	}

}
