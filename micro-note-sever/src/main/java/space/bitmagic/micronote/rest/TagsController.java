package space.bitmagic.micronote.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.bitmagic.micronote.domain.repository.NoteRepository;

import java.util.List;

/**
 * @author yangrd
 * @date 2023/7/10
 **/

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    private final NoteRepository repository;


    @GetMapping("/list")
    public List<String> findAll() {
        return repository.findAllTags();
    }
}
