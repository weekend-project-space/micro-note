package space.bitmagic.micronote.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import space.bitmagic.micronote.application.NoteApplicationService;
import space.bitmagic.micronote.application.NoteCreatedCommand;
import space.bitmagic.micronote.application.NoteDetails;
import space.bitmagic.micronote.domain.NoteSpec;
import space.bitmagic.micronote.domain.repository.NoteRepository;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteRepository repository;

    private final NoteApplicationService service;

    @PostMapping
    public NoteInfo save(@RequestBody NoteCreatedCommand command) {
        return NoteInfo.from(service.save(command));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody NoteCreatedCommand command) {
        service.update(id, command.title(), command.tags(), command.content());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public NoteDetails get(@PathVariable String id) {
        return service.get(id);
    }

    @GetMapping
    public Page<NoteInfo> findAll(String name, String tag, @PageableDefault(sort = "lastModifiedTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return repository.findAll(NoteSpec.toSpec(name, tag), pageable).map(NoteInfo::from);
    }

}
