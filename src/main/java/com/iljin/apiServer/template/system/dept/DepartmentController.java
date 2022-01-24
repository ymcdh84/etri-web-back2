package com.iljin.apiServer.template.system.dept;

import com.iljin.apiServer.core.util.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/dept")
public class DepartmentController {
    private final DepartmentService departmentService;
    @ExceptionHandler(DepartmentException.class)
    public ResponseEntity<Error> deptNotFound(DepartmentException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartmentsByCombo() {
        List<DepartmentDto> list = departmentService.getDepartmentsByCombo();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
