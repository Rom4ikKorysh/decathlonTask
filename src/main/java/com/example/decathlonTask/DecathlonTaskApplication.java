package com.example.decathlonTask;

import com.example.decathlonTask.domain.entity.Note;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DecathlonTaskApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DecathlonTaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addNotes();
	}

	public void addNotes() {
		var note1 = new Note("title1", "content1");
		var note2 = new Note("title2", "content2");
		var note3 = new Note("title3", "content3");
		var note4 = new Note("title4", "content4");
		var note5 = new Note("title5", "content5");
		var note6 = new Note("title6", "content6");
		var note7 = new Note("title7", "content7");
		var note8 = new Note("title8", "content8");
	}

}
