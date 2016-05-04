
package org.tanna.inhouse.EmergencyApp.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.tanna.inhouse.EmergencyApp.Database.Database;
import org.tanna.inhouse.EmergencyApp.Model.EmergencyContact;

public class EmergencyContactServices {
    public List<EmergencyContact> getEmergencyContacts(long id) throws SQLException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        String strSelect = "select * from tbl_emergency_numbers where contact_no = " + id;
        ResultSet rs = Database.selectQuery((String)strSelect);
        while (rs.next()) {
            EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("id"), rs.getLong("contact_no"), rs.getLong("emergency_number"), rs.getBoolean("is_accepted"));
            listEmergencyContacts.add(tempEmerCont);
        }
        return listEmergencyContacts;
    }

    public List<EmergencyContact> getAsEmergencyContact(long id) throws SQLException {
        ArrayList<EmergencyContact> listEmergencyContacts = new ArrayList<EmergencyContact>();
        String strSelect = "select * from tbl_emergency_numbers where emergency_number = " + id;
        ResultSet rs = Database.selectQuery((String)strSelect);
        while (rs.next()) {
            EmergencyContact tempEmerCont = new EmergencyContact(rs.getLong("id"), rs.getLong("contact_no"), rs.getLong("emergency_number"), rs.getBoolean("is_accepted"));
            listEmergencyContacts.add(tempEmerCont);
        }
        return listEmergencyContacts;
    }

    public boolean addEmergencyContact(List<EmergencyContact> list) {
        for (EmergencyContact emergencyContact : list) {
            String str1 = String.valueOf(emergencyContact.getUserContactNo());
            String str2 = String.valueOf(emergencyContact.getEmergencyContactNo());
            boolean isaccepted = emergencyContact.isAccepted();
            String strInsertQuery = "insert into tbl_emergency_numbers (contact_no,emergency_number,is_accepted) values(" + str1 + "," + str2 + "," + isaccepted + ")";
            if (Database.insertQuery((String)strInsertQuery)) continue;
            return false;
        }
        return true;
    }

    public boolean removeEmergencyContact(long number) {
        String strDeleteQuery = "Delete from tbl_emergency_numbers where contact_no =" + number;
        boolean isSuccess = Database.deleteQuery((String)strDeleteQuery);
        if (isSuccess) {
            return true;
        }
        return false;
    }
}
