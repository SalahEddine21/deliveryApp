package com.crafteam.deliveryapp.controllers;

import jakarta.persistence.PessimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PessimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    public String handlePessimisticLockException(PessimisticLockException ex) {
        return "Delivery slot is currently being booked by another customer. Please try again.";
    }
}