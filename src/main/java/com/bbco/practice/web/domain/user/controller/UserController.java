package com.bbco.practice.web.domain.user.controller;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.SelectParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.domain.user.dto.resForm.ResponseUserForm;
import com.bbco.practice.web.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/info")
    public String getUser(@ModelAttribute SelectParam param) {
        log.info("selectParam : {}", param.toString());

        return null;
    }

    @GetMapping("/user/info/{userId}")
    public ResponseEntity<ResponseUserForm> getUserDetail(@PathVariable("userId") String userId) {
        log.info("[사용자 단일 조회]");
        log.info("userId: {}", userId);

        try {
            User user = userService.selectOneUser(userId);

            log.info("[유저 정보] : {}", user.toString());

            ResponseUserForm resForm = new ResponseUserForm(user, "OK");

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }

    @PostMapping("/user/info")
    public ResponseEntity<ResponseUserForm> insertUser(@RequestBody InsertParam param) {
        log.info("[사용자 등록]");
        log.info("param: {}", param.toString());

        try {
            User userInfo = userService.insertUser(param);

            log.info("[유저 정보] : {}", userInfo.toString());

            ResponseUserForm resForm = new ResponseUserForm(userInfo, "OK");

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }

    @PutMapping("/user/info")
    public String updateUser(@RequestBody UpdateParam param) {
        return null;
    }

    @DeleteMapping("/user/info/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        return null;
    }
}
