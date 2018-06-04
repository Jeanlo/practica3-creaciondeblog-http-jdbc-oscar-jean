/*********************************************************
 *  Práctica #3 - HTTP - JDBC (Creación de un blog)       *
 *  Realizada por:                                        *
 *      - Oscar Dionisio Núñez Siri - 2014-0056           *
 *      - Jean Louis Tejeda - 2013-1459                   *
 *  Materia: Programación Web - ISC-415-T-001             *
 *********************************************************/

package Modelos;

public class Etiqueta {
    private long id;
    private String etiqueta;

    public Etiqueta(long id, String etiqueta) {
        this.id = id;
        this.etiqueta = etiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
