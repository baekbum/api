package com.bbco.practice.web.domain.user.controller;

import com.bbco.practice.web.domain.user.dto.UserDto;
import com.bbco.practice.web.domain.user.dto.form.UserListResForm;
import com.bbco.practice.web.domain.user.dto.form.UserResForm;
import com.bbco.practice.web.domain.user.dto.params.UserInsertParam;
import com.bbco.practice.web.domain.user.dto.params.UserSearchCond;
import com.bbco.practice.web.domain.user.dto.params.UserUpdateParam;
import com.bbco.practice.web.domain.user.entity.User;
import com.bbco.practice.web.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResForm> save(@Valid @RequestBody UserInsertParam param) throws Exception {
        User content = userService.save(param);

        return new ResponseEntity<>(new UserResForm(new UserDto(content), "등록되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserListResForm> findById(@RequestBody UserSearchCond cond) throws Exception {
        List<UserDto> content = userService.findUserByCondition(cond);

        return new ResponseEntity<>(new UserListResForm(content, content.size(), "조회되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResForm> findById(@PathVariable("id") String id) throws Exception {
        User content = userService.findById(id);

        return new ResponseEntity<>(new UserResForm(new UserDto(content), "조회되었습니다."), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResForm> update(@PathVariable("id") String id, @RequestBody UserUpdateParam param) throws Exception {
        User content = userService.update(id, param);

        return new ResponseEntity<>(new UserResForm(new UserDto(content), "수정되었습니다."), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserResForm> delete(@PathVariable("id") String id) throws Exception {
        User content = userService.findById(id);

        return new ResponseEntity<>(new UserResForm(new UserDto(content), "삭제되었습니다."), HttpStatus.OK);
    }
}
