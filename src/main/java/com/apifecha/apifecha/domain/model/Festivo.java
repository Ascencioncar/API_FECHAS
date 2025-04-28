package com.apifecha.apifecha.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "festivo")
public class Festivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private Integer dia;

    private Integer mes;

    // <— Mapea explícitamente a la columna 'diaspascua'
    @Column(name = "diaspascua", nullable = false)
    private Integer diasPascua;

    // <— Mapea explícitamente al FK 'idtipo'
    @ManyToOne
    @JoinColumn(name = "idtipo", nullable = false)
    private Tipo tipo;

    public Festivo() {}

    // getters y setters...



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDiasPascua() {
        return diasPascua;
    }

    public void setDiasPascua(Integer diasPascua) {
        this.diasPascua = diasPascua;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
