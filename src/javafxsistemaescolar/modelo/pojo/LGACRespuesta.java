/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author ianca
 */
public class LGACRespuesta {
    private int codigoRespuesta;
    private ArrayList<LGAC> lgac;

    public LGACRespuesta() {
    }

    public LGACRespuesta(int codigoRespuesta, ArrayList<LGAC> lgac) {
        this.codigoRespuesta = codigoRespuesta;
        this.lgac = lgac;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<LGAC> getLgac() {
        return lgac;
    }

    public void setLgac(ArrayList<LGAC> lgac) {
        this.lgac = lgac;
    }
    
    
}
