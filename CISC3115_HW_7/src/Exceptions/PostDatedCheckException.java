package Exceptions;

import java.util.Calendar;

public class PostDatedCheckException extends Exception {
	public PostDatedCheckException(Calendar checkDate) {
        super(String.format(
                "Error: Check is post-dated. Check Date: %02d/%02d/%04d",
                checkDate.get(Calendar.MONTH) + 1,
                checkDate.get(Calendar.DAY_OF_MONTH),
                checkDate.get(Calendar.YEAR)
        ));
    }
	
	
}