package com.om.engine.application.adapters;

import org.apache.calcite.jdbc.CalciteConnection;

import java.nio.file.Path;
import java.sql.SQLException;

@FunctionalInterface
public interface CalciteConnectionProvider {

    CalciteConnection getConnection(Path modelResourcePath) throws SQLException;
}
