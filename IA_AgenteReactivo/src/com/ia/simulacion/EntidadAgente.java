/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.simulacion;

/**
 *
 * @author Carlos
 */
public class EntidadAgente {

    private String coordenadasAgente;
    private boolean existRock = false;
    private int[] moveBlock = new int[4];

    public String getCoordenadasAgente() {
        return coordenadasAgente;
    }

    public void setCoordenadasAgente(String coordenadasAgente) {
        this.coordenadasAgente = coordenadasAgente;
    }

    public boolean isExistRock() {
        return existRock;
    }

    public void setExistRock(boolean existRock) {
        this.existRock = existRock;
    }

    public int[] getMoveBlock() {
        return moveBlock;
    }

    public void setMoveBlock(int[] moveBlock) {
        this.moveBlock = moveBlock;
    }
}
