package com.example.decathlonTask.repository;

import com.example.decathlonTask.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	Optional<Note> findNoteByTitle(String title);

	List<Note> findNotesByTitleContainingIgnoreCase(String searchInput);

}
