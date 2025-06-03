package org.serratec.TrabalhoFinalAPI.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResposta {
    private Integer status;
    private String titulo;
    private LocalDateTime dataHora;
    private List<String> erros;

    public ExceptionResposta(Integer status, String titulo, LocalDateTime dataHora, List<String> erros) {
        this.dataHora = dataHora;
        this.erros = erros;
        this.status = status;
        this.titulo = titulo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

    
}
