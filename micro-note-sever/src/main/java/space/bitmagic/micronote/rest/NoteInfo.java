package space.bitmagic.micronote.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import space.bitmagic.micronote.domain.Note;

import java.time.LocalDateTime;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public record NoteInfo(String id, String title,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDateTime time) {
    public static NoteInfo from(Note note) {
        return new NoteInfo(note.getId(), note.getTitle(), note.getLastModifiedTime());
    }
}