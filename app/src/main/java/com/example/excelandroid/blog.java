package com.example.excelandroid;

public class blog {
    public String cantidad, descripccion, fecha, dibujo,pdf,posicion,frente;
    public int foto;


    public blog(String cantidad, String descripccion, String fecha, String dibujo, int foto,String pdf, String posicion, String frente) {
        this.cantidad = cantidad;
        this.descripccion = descripccion;
        this.fecha = fecha;
        this.dibujo = dibujo;
        this.foto = foto;
        this.pdf=pdf;
        this.posicion=posicion;
        this.frente=frente;

    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDibujo() {
        return dibujo;
    }

    public void setDibujo(String dibujo) {
        this.dibujo = dibujo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }
}
