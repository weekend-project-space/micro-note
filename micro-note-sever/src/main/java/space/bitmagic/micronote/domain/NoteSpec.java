package space.bitmagic.micronote.domain;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author yangrd
 * @date 2023/7/10
 **/
public interface NoteSpec {

    static Specification<Note> toSpec(String name, String tag) {
        return (root, q, cb) ->
                cb.and(
                        Strings.isNotBlank(name) ? cb.like(root.get("title"), "%" + name + "%") : cb.literal(true),
                        Strings.isNotBlank(tag) ? cb.equal(root.join("tags"), tag) : cb.literal(true)
                );
    }
}
