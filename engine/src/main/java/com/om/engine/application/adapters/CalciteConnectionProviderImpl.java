package com.om.engine.application.adapters;

import com.om.engine.application.services.TechnicalException;
import org.apache.calcite.jdbc.CalciteConnection;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CalciteConnectionProviderImpl implements CalciteConnectionProvider {

    @Override
    public CalciteConnection getConnection(Path modelResourcePath) {

        if (modelResourcePath == null) {
            throw new IllegalArgumentException("modelResourcePath cannot be null");
        }

        if (!Files.exists(modelResourcePath)) {
            throw new IllegalArgumentException("modelResourcePath does not exist: " + modelResourcePath);
        }

        Properties info = new Properties();
        info.setProperty("model", modelResourcePath.toString());

        try {
            return DriverManager.getConnection("jdbc:calcite:", info).unwrap(CalciteConnection.class);
        } catch (SQLException e) {
            throw new TechnicalException("Unable to get Calcite connection", e);
        }
    }
}
