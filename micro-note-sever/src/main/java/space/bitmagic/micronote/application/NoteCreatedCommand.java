package space.bitmagic.micronote.application;

import java.util.List;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public record NoteCreatedCommand(String title, List<String> tags, String content) {
}
