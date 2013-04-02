package models;

import com.avaje.ebean.annotation.EmbeddedColumns;
import org.joda.time.LocalDateTime;

import javax.persistence.Id;
import java.io.File;
import java.util.UUID;

/**
 * User: Mike Stetsyshyn
 * Date: 2/5/12
 * Time: 3:59 PM
 */
public class StoredFile {
    @Id
    public UUID id;
    public String fileType;
    public String fileExt;
    public String commentText;
    public LocalDateTime createdAt;

}
