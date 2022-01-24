package com.iljin.apiServer.template.system.emp;

import com.iljin.apiServer.core.security.role.UserRole;
import com.iljin.apiServer.core.security.role.UserRoleRepository;
import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserRepository;
import com.iljin.apiServer.core.util.Util;
import com.iljin.apiServer.template.system.code.CodeDetail;
import com.iljin.apiServer.template.system.code.CodeDto;
import com.iljin.apiServer.template.system.code.CodeService;
import com.iljin.apiServer.template.system.dept.Department;
import com.iljin.apiServer.template.system.dept.DepartmentRepository;
import com.iljin.apiServer.template.system.dept.DepartmentService;
import com.iljin.apiServer.template.system.menu.UserMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final String companyCode = "101600";
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final UserMenuRepository userMenuRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private Util util;

    @Override
    public List<Employee> searchEmployeesEmpNoOrEmpNmOrDeptNm(String value) {
        String loginCompCd = Optional.ofNullable(util.getLoginCompCd()).orElse(companyCode);

        return employeeRepository.getByEmpNoOrEmpNmOrDeptNmContaining(loginCompCd, value);
    }

    @Override
    public List<Employee> getEmpsByDept(String deptCd) {
        String loginCompCd = util.getLoginCompCd();
        if(loginCompCd.equals("")) {
            loginCompCd = companyCode;
        }

        return employeeRepository.findByCompCdAndDeptCdOrderByEmpNmAsc(loginCompCd, deptCd);
    }

    @Override
    public List<EmployeeDto> getEmpDeptList() {
        List<EmployeeDto> list = new ArrayList<>();

        list = employeeRepository.getEmployeeDeptList()
                .stream()
                .map(s -> new EmployeeDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                ))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public List<EmployeeDto> getEmpListByEmpNameOrEmpNo(String value) {
        List<EmployeeDto> list = new ArrayList<>();

        list = employeeRepository.getEmployeesByEmpNameOrEmpNo(value)
                .stream()
                .map(s -> new EmployeeDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[7]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[9]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[10]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[11]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[12]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[13]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[14]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[15]).orElse(""))
                        ,(Boolean) s[16]
                        ,String.valueOf(Optional.ofNullable(s[17]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[18]).orElse(""))
                        ,(LocalDateTime) s[19]
                        ,(LocalDateTime) s[20]
                ))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public EmployeeDto getEmployeeDetailByLoginId(String loginId) {
        List<EmployeeDto> list = new ArrayList<>();

        list = employeeRepository.getEmployeeDetailByLoginId(loginId)
                .stream()
                .map(s -> new EmployeeDto(
                        String.valueOf(Optional.ofNullable(s[0]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[1]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[2]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[3]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[4]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[5]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[6]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[7]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[8]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[9]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[10]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[11]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[12]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[13]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[14]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[15]).orElse(""))
                        ,(Boolean) s[16]
                        ,String.valueOf(Optional.ofNullable(s[17]).orElse(""))
                        ,String.valueOf(Optional.ofNullable(s[18]).orElse(""))
                        ,(LocalDateTime) s[19]
                        ,(LocalDateTime) s[20]
                ))
                .collect(Collectors.toList());

        if(list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public EmployeeDto saveEmployeeByLoginId(String loginId, EmployeeDto employeeDto) {
        /* 1. New Employee or Not */
        EmployeeKey employeeKey = new EmployeeKey(loginId, "101600");
        Optional<Employee> isEmployee = employeeRepository.findById(employeeKey);

        //TODO: Refactoring------

        /* get Company Code */
        CodeDto codeDto = new CodeDto();
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("COMP_CD");
        List<CodeDto> getCompany = codeService.getGroupCodeDetailList(codeDto);
        if(getCompany.isEmpty()) {
            throw new RuntimeException("기준코드에 회사 데이터가 없습니다. (GROUP_CD : 'COMP_CD')");
        }
        /* get Business Place Code */
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("BP_CD");
        List<CodeDto> getBizPlace = codeService.getGroupCodeDetailList(codeDto);
        if(getBizPlace.isEmpty()) {
            throw new RuntimeException("기준코드에 사업장 데이터가 없습니다. (GROUP_CD : 'BP_CD')");
        }
        /* get Department */
        Optional<Department> getDepartment = departmentService.getDepartmentByDeptNo(employeeDto.getDeptCd());
        if(!getDepartment.isPresent()) {
            throw new RuntimeException("부서 정보를 찾을 수 없습니다. (부서코드 : +" + employeeDto.getDeptCd() + ")");
        }
        /* get Department */
        Optional<Department> getAdmrDepartment = departmentService.getDepartmentByDeptNo(employeeDto.getUpperDeptCd());
        if(!getAdmrDepartment.isPresent()) {
            throw new RuntimeException("관리부서(상위부서) 정보를 찾을 수 없습니다. (부서코드 : +" + employeeDto.getUpperDeptCd() + ")");
        }
        /* get Service Code */
        codeDto.setCompCd("101600");
        codeDto.setGroupCd("SERVE_CD");
        codeDto.setDetailCd(employeeDto.getServeCd());
        Optional<CodeDetail> getServeCd = codeService.getGroupCodeDetail(codeDto);
        if(!getServeCd.isPresent()) {
            throw new RuntimeException("기준코드에 재직상태 데이터가 없습니다. (GROUP_CD : 'SERVE_CD', DETAIL_CD : " + employeeDto.getServeCd() + ")");
        }

        if(isEmployee.isPresent()) {
            // update old User

            /* 1. update Employee Entity */
            isEmployee.ifPresent(c -> {
                c.updateEmployee(employeeDto.getEmpNm()
                        ,getDepartment.get().getDeptNo()
                        ,getDepartment.get().getDeptName()
                        ,getAdmrDepartment.get().getDeptNo()
                        ,getAdmrDepartment.get().getDeptName()
                        ,employeeDto.getJobDutCd()
                        ,employeeDto.getJobDutNm()
                        ,employeeDto.getJobGradeCd()
                        ,employeeDto.getJobGradeNm()
                        ,getServeCd.get().getDetailCd()
                        ,getServeCd.get().getDetailNm()
                        ,employeeDto.getEmail()
                        ,employeeDto.getMobTelNo());

                employeeRepository.save(c);
            });

            /* 2. update UserRole Entity */
            userRoleRepository.findRoleByUser_LoginId(loginId)
                    .ifPresent(c -> {
                        c.updateRole(employeeDto.getRole());
                        userRoleRepository.save(c);
                    });

            /* 3. update User Entity */
            userRepository.findByLoginId(loginId)
                    .ifPresent(c -> {
                        c.updateUserDetail(employeeDto.getEmpNm(),
                                employeeDto.getDeptCd(),
                                employeeDto.enableFlag);
                        userRepository.save(c);
                    });
        } else {
            // add New User

            /* 1. create Employee Entity */
            Employee employee = new Employee().builder()
                    .compCd(getCompany.get(0).getDetailCd())
                    .compNm(getCompany.get(0).getDetailNm())
                    .empNo(employeeDto.getEmpNo())
                    .empNm(employeeDto.getEmpNm())
                    .deptCd(getDepartment.get().getDeptNo())
                    .deptNm(getDepartment.get().getDeptName())
                    .bpCd(getBizPlace.get(0).getDetailCd())
                    .bpNm(getBizPlace.get(0).getDetailNm())
                    .upperDeptCd(getAdmrDepartment.get().getDeptNo())
                    .upperDeptNm(getAdmrDepartment.get().getDeptName())
                    .jobDutCd(employeeDto.getJobDutCd())
                    .jobDutNm(employeeDto.getJobDutNm())
                    .jobGradeCd(employeeDto.getJobGradeCd())
                    .jobGradeNm(employeeDto.getJobGradeNm())
                    .serveCd(getServeCd.get().getDetailCd())
                    .serveNm(getServeCd.get().getDetailNm())
                    .email(employeeDto.getEmail())
                    .mobTelNo(employeeDto.getMobTelNo())
                    .build();
            employeeRepository.save(employee);

            /* 2. create User Entity */
            User newUser = new User().newUser(employeeDto.getEmpNo(),
                    passwordEncoder.encode(employeeDto.getPassword()),
                    getCompany.get(0).getDetailCd(),
                    employeeDto.getDeptCd(),
                    employeeDto.getEmpNm(),
                    employeeDto.enableFlag,
                    Long.valueOf(1));
            userRepository.save(newUser);

            /* 3. create UserRole Entity */
            userRepository.findByLoginId(loginId).ifPresent(c -> {
                List<UserRole> newRoles = new ArrayList<>();
                UserRole newRole = new UserRole(
                        c.getId(),
                        c.getCompCd(),
                        employeeDto.getRole(),
                        c);
                userRoleRepository.save(newRole);

                newRoles.add(newRole);
                c.updateUserRoles(newRoles);
                userRepository.save(c);
            });
        }
        return this.getEmployeeDetailByLoginId(loginId);
    }

    @Override
    public String deleteEmployeeByLoginId(String loginId) {
        EmployeeKey employeeKey = new EmployeeKey(loginId, "101600");

        /* 1. on TB_MST_EMP*/
        Optional<Employee> employee = employeeRepository.findById(employeeKey);
        if(employee.isPresent()) {
            employeeRepository.deleteById(employeeKey);
        }

        /* 2. on A_USER_MENU */
        userMenuRepository.deleteByCompCdAndUserId("101600", loginId);

        Optional<User> user = userRepository.findByLoginId(loginId);
        Long userId = null;
        if(user.isPresent()) {
            userId = user.get().getId();
        }

        Optional<UserRole> userRole = userRoleRepository.findByUserId(userId);

        /* 3. on A_USER */
        userRepository.deleteById(userId);

        /* 4. on A_USER_ROLE */
        userRole.ifPresent(c -> {
            userRoleRepository.delete(c);
        });

        return "삭제되었습니다.";
    }
}
