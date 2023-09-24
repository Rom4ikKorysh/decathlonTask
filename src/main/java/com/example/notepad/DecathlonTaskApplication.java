package com.example.notepad;

import com.example.notepad.domain.entity.Note;
import com.example.notepad.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DecathlonTaskApplication implements CommandLineRunner {

	private final NoteRepository noteRepository;

	public DecathlonTaskApplication(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(DecathlonTaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addNotes();
	}

	public void addNotes() {
		List<Note> notes = List.of(
				new Note("title1", "content2"),
				new Note("title2", "content2"),
				new Note("title3", "content3")
				);
		noteRepository.saveAll(notes);
	}

}
