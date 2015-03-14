package be.bouko.export;

import be.bouko.model.JpaEntity;

import java.net.URL;
import java.util.List;

public interface Export {
    URL getExportUrl();

    void export(String fileName, List<JpaEntity> simpsons);
}
