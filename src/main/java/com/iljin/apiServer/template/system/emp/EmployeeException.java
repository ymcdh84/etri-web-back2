package com.iljin.apiServer.template.system.emp;

public class EmployeeException extends RuntimeException {
    EmployeeException() {
        super();
    }

    EmployeeException(String msg) {
        super(msg);
    }
}
