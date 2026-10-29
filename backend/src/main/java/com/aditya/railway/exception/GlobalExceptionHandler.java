package com.aditya.railway.exception;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ApiException.class) ResponseEntity<Map<String,Object>> api(ApiException e) { return ResponseEntity.status(e.getStatus()).body(body(e.getMessage())); }
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e) {
   Map<String,String> fields=new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x->fields.put(x.getField(),x.getDefaultMessage()));
   Map<String,Object> out=body("Validation failed"); out.put("fields",fields); return ResponseEntity.badRequest().body(out);
 }
 @ExceptionHandler(DuplicateKeyException.class) ResponseEntity<Map<String,Object>> duplicate(DuplicateKeyException e) { return ResponseEntity.status(409).body(body("A record with that unique value already exists.")); }
 @ExceptionHandler(Exception.class) ResponseEntity<Map<String,Object>> other(Exception e) { return ResponseEntity.status(500).body(body("Unexpected server error. Please try again.")); }
 private Map<String,Object> body(String message) { Map<String,Object> m=new LinkedHashMap<>(); m.put("timestamp", Instant.now()); m.put("message",message); return m; }
}
