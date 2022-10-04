package com.hsba.bi.fitnessstudio.Fitnessstudio.web;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Fehlermeldung, falls Trainer nicht gefunden werden konnte
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainerNotFoundException extends RuntimeException{
}
