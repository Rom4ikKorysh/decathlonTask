package com.example.notepad.web;

import com.example.notepad.domain.dto.CreateOrUpdateNoteRequest;
import com.example.notepad.domain.dto.NoteDto;
import com.example.notepad.domain.entity.Note;
import com.example.notepad.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
	public ResponseEntity<List<NoteDto>> getAllNotes() {
		List<NoteDto> notes = noteService.getAllNotes();
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
	ResponseEntity<List<NoteDto>> findNotesByTitle(@RequestParam(value = "title") String title) {
		List<NoteDto> notes = noteService.findNotesByTitleContains(title);
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
	public ResponseEntity<String> createNote(@RequestBody @Valid CreateOrUpdateNoteRequest createOrUpdateNoteRequest, UriComponentsBuilder uriComponentsBuilder) {
		Long noteId = noteService.createNote(createOrUpdateNoteRequest);
		UriComponents uriComponents = uriComponentsBuilder.path("/notes/{id}").buildAndExpand(noteId);

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
			summary = "Update note",
			description = "Updates notes values by id.",
			tags = {"notes"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PutMapping("/{id}")
	public ResponseEntity<String> updateNote(@PathVariable Long id,
											 @Valid @RequestBody CreateOrUpdateNoteRequest note) {
		noteService.updateNote(note, id);
		return ResponseEntity.ok("Note with id " + id + " has been updated");
	}

}
