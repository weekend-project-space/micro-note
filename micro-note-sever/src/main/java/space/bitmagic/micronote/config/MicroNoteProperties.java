package space.bitmagic.micronote.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangrd
 * @date 2023/7/11
 **/
@Data
@ConfigurationProperties(prefix = "config.md.file")
public class MicroNoteProperties {

    private Integer maxSize;

    private String path;
}
