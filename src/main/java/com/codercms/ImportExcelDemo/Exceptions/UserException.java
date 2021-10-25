package com.codercms.ImportExcelDemo.Exceptions;

import java.util.List;

/**
 * Custom Exception to throw error messages.
 */
public class UserException extends RuntimeException{
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}


