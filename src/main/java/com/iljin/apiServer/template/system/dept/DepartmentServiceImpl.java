package com.iljin.apiServer.template.system.dept;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getDepartmentsByCombo() {
        List<DepartmentDto> list = departmentRepository.getDepartmentsByCombo()
                .stream()
                .map(s -> new DepartmentDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                ))
                .collect(Collectors.toList())
                ;
        return list;
    }

    @Override
    public Optional<Department> getDepartmentByDeptNo(String deptCd) {
        return departmentRepository.findByDeptNo(deptCd);
    }
}
