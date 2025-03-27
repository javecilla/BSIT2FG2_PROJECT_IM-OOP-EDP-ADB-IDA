package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface FIConnectionSupplier {
    Connection get() throws SQLException;
}