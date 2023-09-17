package com.outfit7.fun7.controller;

import com.outfit7.fun7.service.admin.AdminService;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

  public final AdminService adminService;

  @GetMapping("/users")
  public ResponseEntity<?> listAllUsers() {
    try {
      return ResponseEntity.ok(adminService.getAllUsers());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable UUID id) {
    try {
      return ResponseEntity.ok(adminService.getUser(id));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<String> deleteUser(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable UUID id) {
    try {
      final String BEARER = "Bearer";
      final String accessToken = authorizationHeader.substring(BEARER.length() + 1);
      adminService.deleteUser(accessToken, id);
      return ResponseEntity.ok("User with id '%s' successfully deleted.".formatted(id));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
