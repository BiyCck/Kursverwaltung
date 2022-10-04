package com.hsba.bi.fitnessstudio.Fitnessstudio.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Fehlermeldung für unberechtigte Seitenbesuche
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{
}
