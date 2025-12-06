package Exceptions;

import java.util.Calendar;

public class CheckTooOldException extends Exception {
    public CheckTooOldException(String message) {
        super(message);
    }

    public CheckTooOldException(Calendar dateOfCheck, Calendar sixMonthsAgo) {
        super(String.format(
            "Error: Check is too old (older than 6 months). " +
            "Check Date: %02d/%02d/%04d. " +
            "Oldest Acceptable Date: %02d/%02d/%04d.",
            dateOfCheck.get(Calendar.MONTH) + 1,
            dateOfCheck.get(Calendar.DAY_OF_MONTH),
            dateOfCheck.get(Calendar.YEAR),
            sixMonthsAgo.get(Calendar.MONTH) + 1,
            sixMonthsAgo.get(Calendar.DAY_OF_MONTH),
            sixMonthsAgo.get(Calendar.YEAR)
        ));
    }
}