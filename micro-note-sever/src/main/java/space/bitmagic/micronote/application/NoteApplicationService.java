package space.bitmagic.micronote.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.bitmagic.micronote.config.MicroNoteProperties;
import space.bitmagic.micronote.domain.Note;
import space.bitmagic.micronote.domain.repository.NoteRepository;
import space.bitmagic.micronote.infrastructure.utils.FileUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
@Service
@Transactional(rollbackFor = Exception.class)

@RequiredArgsConstructor
public class NoteApplicationService {


    private final NoteRepository repository;

    private final MicroNoteProperties properties;

    public Note save(NoteCreatedCommand command) {
        checkContentSize(command.content());
        var fileName = genFileName(command.title());
        FileUtils.write(properties.getPath() + fileName, command.content());
        return repository.save(Note.of(command.title(), command.tags(), fileName));
    }

    public void delete(String id) {
        var note = repository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        FileUtils.delete(properties.getPath() + note.getFileName());
        repository.deleteById(id);
    }

    public void update(String id, String title, List<String> tags, String content) {
        checkContentSize(content);
        var note = repository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        var fileName = note.getFileName();
        if (title != null && !title.equals(note.getTitle())) {
            FileUtils.delete(properties.getPath() + fileName);
            fileName = genFileName(title);
        }
        FileUtils.write(properties.getPath() + fileName, content);
        note.setTitle(title);
        note.setTags(tags);
        note.setFileName(fileName);
        repository.save(note);
    }

    public NoteDetails get(String id) {
        return repository.findById(id).map(note -> {
            var content = FileUtils.read(properties.getPath() + note.getFileName());
            return NoteDetails.of(note.getId(), note.getTitle(), content);
        }).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    String genFileName(String title) {
        var b = repository.existsById(Note.genId(title + ".md"));
        if (b) {
            return genFileName(title + UUID.randomUUID().toString().substring(0, 4));
        } else {
            return title.replaceAll("/", "-") + ".md";
        }
    }


    private void checkContentSize(String content) {
        if (getStringFileSize(content) > properties.getMaxSize()) {
            throw new RuntimeException("Note size is too large");
        }
    }

    private static int getStringFileSize(String content) {
        return content.getBytes(StandardCharsets.UTF_8).length;
    }


}
