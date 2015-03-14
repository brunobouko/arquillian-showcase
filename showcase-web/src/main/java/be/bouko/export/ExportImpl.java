package be.bouko.export;

import be.bouko.model.JpaEntity;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Stateless
public class ExportImpl implements Export {
    @Resource(name = "url/export")
    private URL exportUrl;

    @Override
    public URL getExportUrl() {
        return exportUrl;
    }

    @Override
    public void export(String fileName, List<JpaEntity> jpaEntities) {
        FileWriter fileWriter = null;
        try {
            File exported = new File(new File(exportUrl.toURI()), fileName + ".txt");
            fileWriter = new FileWriter(exported);
            for (JpaEntity jpaEntity : jpaEntities) {
                fileWriter.append(jpaEntity.getId()).append(" | ").append(jpaEntity.getFirstName()).append(" | ").append(jpaEntity.getName()).append("\n");
            }
            fileWriter.flush();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }
}
