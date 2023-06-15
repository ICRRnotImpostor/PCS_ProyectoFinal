/* 
Autor: Ian Rumayor. 
Creado: 28/05/2023 
Modificado: 14/06/2023  
Descripción: respuesta de LGAC
*/
package javafxsistemaescolar.modelo.pojo;

import java.util.ArrayList;

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
