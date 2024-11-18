package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierService {
    protected boolean isSupplierExists(Connection conn, int supplierId) throws SQLException {
        String query = "SELECT COUNT(*) FROM SUPPLIER WHERE Supplier_ID = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
