package be.bouko.export;

import be.bouko.model.JpaEntity;

import java.net.URL;
import java.util.List;

/**
 * Created by BBOUKO on 14/03/2015.
 */
public interface Export {
    URL getExportUrl();

    void export(String fileName, List<JpaEntity> simpsons);
}
