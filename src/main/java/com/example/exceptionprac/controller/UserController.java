package com.example.exceptionprac.controller;

import com.example.exceptionprac.common.PageRequest;
import com.example.exceptionprac.dto.AccountDto;
import com.example.exceptionprac.dto.AccountSearchType;
import com.example.exceptionprac.service.AccountSearchService;
import com.example.exceptionprac.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final AccountService accountService;
    private final AccountSearchService accountSearchService;

    public UserController(AccountService accountService, AccountSearchService accountSearchService) {
        this.accountService = accountService;
        this.accountSearchService = accountSearchService;
    }

    // @Valid를 활용하여 유효성 검증이 실패했을 경우 > MethodArgumentNotValidException 발생
    @PostMapping("/signup/v1")
    public ResponseEntity signup (@RequestBody @Valid AccountDto.SignUpReq dto, BindingResult result) {

        // BindingResult를 통해 에러 발생시, 에러 내용을 body에 담아 응답
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                // 클라이언트 응답용 데이터
                sb.append("field: " + field.getField() + " ");
                sb.append("message: " + message).append("\n");
                // 서버 확인용 로그
                log.info("field: " + field.getField() + " ");
                log.info("message: " + message + "\n");
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }
        return ResponseEntity.ok(accountService.create(dto));
    }

    // @ControllerAdvice를 통한 예외처리
    // 성공 시, 아래 리턴 값 반환
    // 실패 시, ErrorExceptionController에서 예외 값 반환
    @PostMapping("/signup/v2")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity signup (@RequestBody @Valid final AccountDto.SignUpReq dto) {
        return ResponseEntity.ok(accountService.create(dto));
    }

    @GetMapping("/userinfo/v1/{id}")
    public ResponseEntity show (@PathVariable Long id) {
        accountService.findById(id);
        return ResponseEntity.ok("회원 정보 조회 성공!");
    }

    // Spring Data JPA를 활용한 페이징 처리
    // 이를 통해 데이터베이스마다 페이징 쿼리가 다른 부분들은 신경쓰지 않고 쉽게 구현할 수 있다는 장점이 있음!
    @GetMapping("/page/v1")
    public Page<AccountDto.Res> getAccounts(Pageable pageable){
        return accountService.findAll(pageable).map(AccountDto.Res::new);

        // 페이징 처리 시, 일부 응답 값 설명
            // "last" : true - 마지막 페이지 여부
            // "totalPages" : 1 - 전체 페이지 수
            // "totalElements" : 13 - 모든 요소의 수
            // "size" : 한 페이지에서 보여줄 수 있는 요소의 수 > 디폴트 20
            // "first" : true - 첫 페이지 여부
            // "empty" : false - 리스트가 비어 있는지 여부
    }

    // Spring Data JPA를 활용한 페이징 처리_개선 (PageRequest 객체 사용하여 세부 옵션 설정)
    @GetMapping("/page/v2")
    public Page<AccountDto.Res> getAccounts2(PageRequest pageable) {
        return accountService.findAll(pageable.of()).map(AccountDto.Res::new);
    }

    // Spring Data JPA + QueryDsl을 활용한 검색 + 페이징 처리
    @GetMapping("page/v3")
    public Page<AccountDto.Res> getAccounts3 (
            @RequestParam(name = "type") AccountSearchType type, // 검색 페이징을 위한 type (이메일, 이름, 전체)
            @RequestParam(name = "value", required = false) String value, // type에 대한 value 의미 (검색하고자 하는 값)
            PageRequest pageable) {
        return accountSearchService.search(type, value, pageable.of()).map(AccountDto.Res::new);
    }

}
