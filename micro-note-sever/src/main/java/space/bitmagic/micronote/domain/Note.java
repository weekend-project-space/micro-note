package space.bitmagic.micronote.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import space.bitmagic.micronote.infrastructure.utils.IdGenerator;

import java.util.Collection;

/**
 * @author yangrd
 * @date 2023/7/8
 **/

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "m_note")
public class Note extends AbstractEntity {

    private String title;


    String fileName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "m_note_tags", joinColumns = @JoinColumn(name = "note_id"))
    Collection<String> tags;

    Note(String title, Collection<String> tags, String fileName) {
        setId(genId(fileName));
        this.title = title;
        this.tags = tags;
        this.fileName = fileName;
    }

    public static Note of(String title, Collection<String> tags, String fileName) {
        return new Note(title, tags, fileName);
    }

    public static String genId(String fileName) {
        return IdGenerator.genId(fileName);
    }

}
