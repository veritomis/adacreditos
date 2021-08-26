public EstadoPrestamoEnum getEstadoId() {

    return EstadoPrestamoEnum.parse(this.estadoId); //devuelve el estado del prestamo. EJ: Ingresa 1, devuelve SOLICITADO
}

public void setEstadoId(EstadoPrestamoEnum estadoId) {
    this.estadoId = estadoId.getValue(); //En este caso, si escribo SOLICITADO, devuelve 1
}
public enum EstadoPrestamoEnum {
    SOLICITADO(1),
    RECHAZADO(2), 
    PENDIENTE_APROBACION(3), 
    APROBADO(4), 
    INCOBRABLE(5), 
    CANCELADO(6),
    PREAPROBADO(100);

    private final int value;

    // NOTE: Enum constructor tiene que estar en privado
    private EstadoPrestamoEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EstadoPrestamoEnum parse(int id) {
        EstadoPrestamoEnum status = null; // Default
        for (EstadoPrestamoEnum item : EstadoPrestamoEnum.values()) {
            if (item.getValue() == id) {
                status = item;
                break;
            }
        }
        return status;
    }
}

}