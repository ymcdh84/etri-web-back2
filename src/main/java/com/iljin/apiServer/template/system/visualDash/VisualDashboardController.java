package com.iljin.apiServer.template.system.visualDash;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interface")
public class VisualDashboardController {
    private final VisualDashboardService visualDashboardService;

    /**
     * Subject : save Dashboard ID by login user
     * Menu : System - Dashboard Management
     * */
    @PostMapping("/dashboard")
    public ResponseEntity<String> setDashboardByLoginId(@RequestBody VisualDashboardDto visualDashboardDto) {
        String result = visualDashboardService.setDashboardByLoginId(visualDashboardDto.getDashboardId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Subject : save Dashboard ID by login user
     * Menu : System - Dashboard Management
     * */
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboardByLoginId() {
        String result = visualDashboardService.getDashboardByLoginId();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
