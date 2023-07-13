package space.bitmagic.micronote.application;

/**
 * @author yangrd
 * @date 2023/7/8
 **/
public record NoteDetails(String id, String title, String content) {

    public static NoteDetails of(String id, String title, String content) {
        return new NoteDetails(id, title, content);
    }
}
