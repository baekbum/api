package com.bbco.practice.web.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserController {

//    private final UserService userService;
//
//    @PostMapping("/user/info")
//    public ResponseEntity<ResponseUserForm> insertUser(@Valid @RequestBody InsertParam param) throws Exception {
//        User userInfo = userService.insert(param);
//        ResponseUserForm resForm = new ResponseUserForm(userInfo, "등록되었습니다.");
//
//        return new ResponseEntity<>(resForm, HttpStatus.OK);
//    }
//
//    @GetMapping("/user/info/{userId}")
//    public ResponseEntity<ResponseUserForm> selectUser(@PathVariable("userId") String userId) throws Exception {
//        User user = userService.select(userId);
//        ResponseUserForm resForm = new ResponseUserForm(user, "조회되었습니다.");
//
//        return new ResponseEntity<>(resForm, HttpStatus.OK);
//    }
//
//    @PutMapping("/user/info/{userId}")
//    public ResponseEntity updateUser(@PathVariable("userId") String userId, @RequestBody UpdateParam param) throws Exception {
//        User userInfo = userService.update(userId, param);
//        ResponseUserForm resForm = new ResponseUserForm(userInfo, "수정되었습니다.");
//
//        return new ResponseEntity<>(resForm, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/user/info/{userId}")
//    public ResponseEntity deleteUser(@PathVariable("userId") String userId) throws Exception {
//        User userInfo = userService.delete(userId);
//        ResponseUserForm resForm = new ResponseUserForm(userInfo, "삭제되었습니다.");
//
//        return new ResponseEntity<>(resForm, HttpStatus.OK);
//    }
}
