package com.example.notepad.service;

import com.example.notepad.domain.dto.CreateOrUpdateNoteRequest;
import com.example.notepad.domain.dto.NoteDto;
import com.example.notepad.domain.entity.Note;
import com.example.notepad.exception.AlreadyExistsException;
import com.example.notepad.exception.NotFoundException;
import com.example.notepad.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);
	private final NoteRepository noteRepository;

	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	public Note findById(Long id) {
		return noteRepository.findById(id).orElseThrow(() -> {
			LOGGER.error("Note with id " + id + " not found");
			return new NotFoundException("Note with id " + id + " not found");
		});
	}

	public Long createNote(CreateOrUpdateNoteRequest createOrUpdateNoteRequest) {
		createNoteValidation(createOrUpdateNoteRequest.getTitle());
		Note note = new Note(createOrUpdateNoteRequest.getTitle(), createOrUpdateNoteRequest.getContent());

		Note savedNote = noteRepository.save(note);
		LOGGER.info("Note saved with id " + savedNote.getId());
		return savedNote.getId();
	}

	private void createNoteValidation(String title) {
		noteRepository.findNoteByTitleIgnoreCase(title)
				.ifPresent((note -> {
					LOGGER.error("Note with this title " + title + " already exists");
					throw new AlreadyExistsException("Note with this title " + title + " already exists");
				}));
	}

	public void deleteNoteById(Long id) {
		Note note = findById(id);
		noteRepository.delete(note);
		LOGGER.info("Note with id " + id + " successfully deleted");
	}

	public void updateNote(CreateOrUpdateNoteRequest updateNoteRequest, Long id) {
		Note note = findById(id);

		note.setTitle(updateNoteRequest.getTitle());
		note.setContent(updateNoteRequest.getContent());
		note.setLastModificationDate(LocalDateTime.now());
		noteRepository.save(note);
	}

	public List<NoteDto> findNotesByTitleContains(String searchInput) {
		return noteRepository.findNotesByTitleContainingIgnoreCase(searchInput)
				.stream()
				.map(NoteDto::noteToNoteDto)
				.toList();

	}

	public List<NoteDto> getAllNotes() {
		return noteRepository.findAll()
				.stream()
				.map(NoteDto::noteToNoteDto)
				.toList();
	}

}
