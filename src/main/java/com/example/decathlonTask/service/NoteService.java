package com.example.decathlonTask.service;

import com.example.decathlonTask.domain.dto.CreateNoteRequest;
import com.example.decathlonTask.domain.entity.Note;
import com.example.decathlonTask.exception.AlreadyExistsException;
import com.example.decathlonTask.exception.NotFoundException;
import com.example.decathlonTask.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
	private final NoteRepository noteRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	public Note findById(Long id) {
		return noteRepository.findById(id).orElseThrow(() -> new NotFoundException("Note with id " + id + " not found"));
	}

	private void createNoteValidation(String title) {
		Optional<Note> optionalNote = noteRepository.findNoteByTitle(title);
		if (optionalNote.isPresent()) {
			throw new AlreadyExistsException("Note with this title " + title + " already exists");
		}
	}

	public Note createNote(CreateNoteRequest createNoteRequest) {
		createNoteValidation(createNoteRequest.getTitle());
		Note note = new Note(createNoteRequest.getTitle(), createNoteRequest.getContent());
		note.setLastModificationDate(LocalDateTime.now());

		noteRepository.save(note);
		return note;
	}


	public void deleteNoteById(Long id) {
		Note note = findById(id);
		noteRepository.delete(note);
		LOGGER.info("Note with id " + id + " successfully deleted");
	}

	public Note updateNote(Note note) {
		findById(note.getId());
		note.setLastModificationDate(LocalDateTime.now());
		return noteRepository.save(note);
	}

	public List<Note> findNotesByTitleContains(String searchInput) {
		List<Note> notes = noteRepository.findNotesByTitleContainingIgnoreCase(searchInput);
		if (notes.isEmpty()) {
			throw new NotFoundException("Notes with title " + searchInput + " not found");
		}
		return notes;
	}

	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

}
