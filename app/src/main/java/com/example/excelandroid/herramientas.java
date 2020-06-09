package com.example.excelandroid;

public class herramientas {

    public  String descripccion_herramienta,fecha_salida;

    public herramientas(String descripccion_herramienta, String fecha_salida) {
        this.descripccion_herramienta = descripccion_herramienta;
        this.fecha_salida = fecha_salida;
    }

    public String getDescripccion_herramienta() {
        return descripccion_herramienta;
    }

    public void setDescripccion_herramienta(String descripccion_herramienta) {
        this.descripccion_herramienta = descripccion_herramienta;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }
}
