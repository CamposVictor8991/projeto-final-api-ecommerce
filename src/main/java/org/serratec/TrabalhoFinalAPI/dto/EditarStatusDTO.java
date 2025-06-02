package org.serratec.TrabalhoFinalAPI.dto;

import org.serratec.TrabalhoFinalAPI.enuns.Status;

public class EditarStatusDTO {
    private Status status;
    private double desconto;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
