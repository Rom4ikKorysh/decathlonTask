package com.example.decathlonTask.web;

import com.example.decathlonTask.domain.dto.CreateNoteRequest;
import com.example.decathlonTask.domain.entity.Note;
import com.example.decathlonTask.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

	private final NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	@Operation(
			summary = "Get All Notes",
			description = "Retrieves a list of all notes.",
			tags = {"notes"})
	@GetMapping
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> notes = noteService.getAllNotes();
		return ResponseEntity.ok(notes);
	}

	@Operation(
			summary = "Find Notes by Title",
			description = "Retrieves notes containing the specified title.",
			tags = {"notes"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Note.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(value = "/findByTitle")
	ResponseEntity<List<Note>> findNotesByTitle(@RequestParam(value = "title") String title) {
		List<Note> notes = noteService.findNotesByTitleContains(title);
		return ResponseEntity.ok(notes);
	}

	@Operation(
			summary = "Create a Note",
			description = "Creates a new note with the provided data.",
			tags = {"notes"})
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Created: Note created successfully"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping
	public ResponseEntity<Note> createNote(@RequestBody CreateNoteRequest createNoteRequest, UriComponentsBuilder uriComponentsBuilder) {
		Note note = noteService.createNote(createNoteRequest);
		UriComponents uriComponents = uriComponentsBuilder.path("/notes/{id}").buildAndExpand(note.getId());

		var location = uriComponents.toUri();

		return ResponseEntity.created(location).build();
	}

	@Operation(
			summary = "Delete a Note by ID",
			description = "Deletes a note by its ID.",
			tags = {"notes"})
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No Content: Note deleted successfully"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteNoteById(@PathVariable Long id) {
		noteService.deleteNoteById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Edit Note Content",
			description = "Updates the content of a note by its ID.",
			tags = {"notes"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Note.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Note> editNoteContent(@PathVariable Long id, @RequestBody Note note) {
		note.setId(id);
		return ResponseEntity.ok(noteService.updateNote(note));
	}

}
