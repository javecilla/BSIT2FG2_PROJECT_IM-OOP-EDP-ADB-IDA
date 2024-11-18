package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierService {
    public boolean isSupplierExists(int supplierId) throws SQLException {
        Connection conn = null;
        String query = "SELECT COUNT(*) FROM SUPPLIER WHERE Supplier_ID = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, supplierId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
