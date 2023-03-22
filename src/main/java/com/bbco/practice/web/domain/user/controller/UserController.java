package com.bbco.practice.web.domain.user.controller;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import com.bbco.practice.web.domain.user.dto.resForm.ResponseUserForm;
import com.bbco.practice.web.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/info")
    public ResponseEntity<ResponseUserForm> insertUser(@Valid @RequestBody InsertParam param) {
        try {
            User userInfo = userService.insert(param);

            ResponseUserForm resForm = new ResponseUserForm(userInfo, "등록되었습니다.");

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }

    @GetMapping("/user/info/{userId}")
    public ResponseEntity<ResponseUserForm> selectUser(@PathVariable("userId") String userId) {
        try {
            User user = userService.select(userId);

            ResponseUserForm resForm = new ResponseUserForm(user, "조회되었습니다.");

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }

    @PutMapping("/user/info/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") String userId, @RequestBody UpdateParam param) {
        try {
            User userInfo = userService.update(userId, param);

            ResponseUserForm resForm = new ResponseUserForm(userInfo, "수정되었습니다.");

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }

    @DeleteMapping("/user/info/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") String userId) {
        try {
            User userInfo = userService.delete(userId);

            ResponseUserForm resForm = new ResponseUserForm(userInfo, "삭제되었습니다.");

            return new ResponseEntity<>(resForm, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        } catch (Exception e) {
            throw new RuntimeException("서버 오류입니다.");
        }
    }
}
