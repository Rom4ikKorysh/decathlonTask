package com.example.decathlonTask.service;

import com.example.decathlonTask.domain.dto.CreateNoteRequest;
import com.example.decathlonTask.domain.entity.Note;
import com.example.decathlonTask.exception.AlreadyExistsException;
import com.example.decathlonTask.exception.NotFoundException;
import com.example.decathlonTask.repository.NoteRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

	@Mock
	private NoteRepository noteRepository;
	private NoteService noteService;

	Long noteId = 1L;
	Note note = new Note("test1", "test1");

	@BeforeEach
	void setUp() {
		noteService = new NoteService(noteRepository);
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
		CreateNoteRequest createNoteRequest = new CreateNoteRequest(note.getTitle(), note.getContent());

		when(noteRepository.findNoteByTitle(note.getTitle())).thenReturn(Optional.empty());

		noteService.createNote(createNoteRequest);

		verify(noteRepository).save(any(Note.class));
	}

	@Test
	void createNoteWhenNoteTitleIsExists() {
		CreateNoteRequest createNoteRequest = new CreateNoteRequest(note.getTitle(), note.getContent());

		when(noteRepository.findNoteByTitle(note.getTitle())).thenReturn(Optional.of(note));

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> noteService.createNote(createNoteRequest));
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

		Note updatedNote = noteService.updateNote(note);

		verify(noteRepository).save(updatedNote);
	}

	@Test
	void findNotesByTitleContaining() {
		List<Note> notes = new ArrayList<>();
		notes.add(note);

		when(noteRepository.findNotesByTitleContainingIgnoreCase("t")).thenReturn(notes);

		List<Note> returnedNotes = noteService.findNotesByTitleContains("t");

		assertEquals(returnedNotes.size(), notes.size());
	}

	@Test
	void findAllNotes() {
		noteService.getAllNotes();

		verify(noteRepository).findAll();
	}

}
