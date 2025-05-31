package org.serratec.TrabalhoFinalAPI.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmailException.class, SenhaException.class})
	protected ResponseEntity<Object> handleEmailESenhaException(RuntimeException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                List<String> erros = new ArrayList<>();
                for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
                    erros.add(erro.getField()+": "+ erro.getDefaultMessage());
                }

        return super.handleExceptionInternal(ex,
            new ExceptionResposta(status.value(),
            "Campos inválidos. Por favor, confira o preenchimento dos dados",
            LocalDateTime.now(), erros),
            headers,
            status,
            request);
    }
}
