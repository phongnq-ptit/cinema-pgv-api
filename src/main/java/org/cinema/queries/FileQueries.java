package org.cinema.queries;

import static org.cinema.jooq.tables.Files.FILES;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.cinema.models.dto.FileDto;
import org.cinema.utils.CommonUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class FileQueries {
  @Autowired private final DSLContext dsl;

  public FileQueries(DSLContext dsl) {
    this.dsl = dsl;
  }

  public FileDto insertSingleFile(FileDto file) {
    UUID uuid = UUID.randomUUID();
    dsl.insertInto(FILES, FILES.UUID, FILES.FILE_NAME, FILES.URL, FILES.SIZE, FILES.TYPE)
        .values(
            CommonUtils.uuidToBytesArray(uuid),
            file.getFileName(),
            file.getUrl(),
            file.getSize(),
            file.getType().getType())
        .execute();

    file.setUuid(uuid);
    return file;
  }

  public List<FileDto> insertMultiFiles(List<FileDto> files) {
    List<FileDto> newListFiles = new ArrayList<>();
    for (FileDto file : files) {
      FileDto _file = insertSingleFile(file);
      newListFiles.add(_file);
    }
    return newListFiles;
  }
}
