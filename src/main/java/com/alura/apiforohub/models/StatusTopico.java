package com.alura.apiforohub.models;

public enum StatusTopico {
    ACTIVO(new String[]{"activo","act","true","ok","si"}),
    INACTIVO(new String[]{"inactivo","inact","false","no"});

    public String[] statusTopico;

    public String[] getStatusTopico(){
        return statusTopico;
    }

    StatusTopico(String[] statusTopico){
        this.statusTopico=statusTopico;
    }

    public static StatusTopico textoEspañol(String texto) {
        for (StatusTopico status : StatusTopico.values()) {
            for (String statusString : status.getStatusTopico()) {
                if (statusString.equalsIgnoreCase(texto)) {
                    return status;
                }
            }
        }
        throw new IllegalArgumentException("los valores posibles para ACTIVO son \"activo\",\"act\",\"true\",\"ok\",\"si\" y para INACTIVO \"inactivo\",\"inact\",\"false\",\"no\" " + texto);
    }
}
