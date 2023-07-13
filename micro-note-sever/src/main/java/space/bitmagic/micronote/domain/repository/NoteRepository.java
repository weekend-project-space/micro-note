package space.bitmagic.micronote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import space.bitmagic.micronote.domain.Note;

import java.util.List;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public interface NoteRepository extends JpaRepository<Note, String>, JpaSpecificationExecutor<Note> {

    @Query(value = "select distinct tags from m_note_tags", nativeQuery = true)
    List<String> findAllTags();
}
