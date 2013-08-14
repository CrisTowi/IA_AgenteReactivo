/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.agente;

/**
 *
 * @author Carlos
 */
public interface AgenteReactivo {

    public void Movimientos(int noAgente);

    public String[] Analizar(int noAgente);

    public boolean BuscarNave(int noAgente);
}
