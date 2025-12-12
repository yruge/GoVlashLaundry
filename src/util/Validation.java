package util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Validation {

    public static boolean isAdult(Date dob, int minAge) {
        if (dob == null) return false;
        LocalDate birth = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birth, LocalDate.now()).getYears() >= minAge;
    }

    public static boolean validEmployeeEmail(String email) {
        return email != null && email.endsWith("@govlash.com");
    }

    public static boolean validCustomerEmail(String email) {
        return email != null && email.endsWith("@email.com");
    }
}
