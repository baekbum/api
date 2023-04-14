package com.bbco.practice.web.domain.admin.controller;

import com.bbco.practice.web.domain.admin.dto.AdminDto;
import com.bbco.practice.web.domain.admin.dto.form.AdminListResForm;
import com.bbco.practice.web.domain.admin.dto.form.AdminResForm;
import com.bbco.practice.web.domain.admin.dto.params.AdminInsertParam;
import com.bbco.practice.web.domain.admin.dto.params.AdminSearchCond;
import com.bbco.practice.web.domain.admin.dto.params.AdminUpdateParam;
import com.bbco.practice.web.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService service;

    @PostMapping("/admin")
    public ResponseEntity<AdminResForm> save(@Valid @RequestBody AdminInsertParam param) throws Exception {
        AdminDto content = service.adminToDto(service.save(param));

        return new ResponseEntity<>(new AdminResForm(content, "등록되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<AdminListResForm> findByCondition(@RequestBody AdminSearchCond cond) throws Exception{
        List<AdminDto> content = service.adminToDto(service.findAdminByCondition(cond));

        return new ResponseEntity<>(new AdminListResForm(content, content.size(), "조회되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<AdminResForm> findById(@PathVariable("id") String id) throws Exception{
        AdminDto content = service.adminToDto(service.findById(id));

        return new ResponseEntity<>(new AdminResForm(content, "조회되었습니다."), HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<AdminResForm> update(@PathVariable("id") String id, @Valid @RequestBody AdminUpdateParam param) {
        AdminDto content = service.adminToDto(service.update(id, param));

        return new ResponseEntity<>(new AdminResForm(content, "수정되었습니다."), HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<AdminResForm> delete(@PathVariable("id") String id) {
        AdminDto content = service.adminToDto(service.delete(service.findById(id)));

        return new ResponseEntity<>(new AdminResForm(content, "삭제되었습니다.."), HttpStatus.OK);
    }

}
