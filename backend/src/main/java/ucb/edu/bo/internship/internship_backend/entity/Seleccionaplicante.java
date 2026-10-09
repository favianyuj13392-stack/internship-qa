/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "seleccionaplicante")
@NamedQueries({
    @NamedQuery(name = "Seleccionaplicante.findAll", query = "SELECT s FROM Seleccionaplicante s"),
    @NamedQuery(name = "Seleccionaplicante.findByIdseleccionaplicante", query = "SELECT s FROM Seleccionaplicante s WHERE s.idseleccionaplicante = :idseleccionaplicante"),
    @NamedQuery(name = "Seleccionaplicante.findByFechaseleccion", query = "SELECT s FROM Seleccionaplicante s WHERE s.fechaseleccion = :fechaseleccion"),
    @NamedQuery(name = "Seleccionaplicante.findByHoraseleccion", query = "SELECT s FROM Seleccionaplicante s WHERE s.horaseleccion = :horaseleccion"),
    @NamedQuery(name = "Seleccionaplicante.findByComentarios", query = "SELECT s FROM Seleccionaplicante s WHERE s.comentarios = :comentarios"),
    @NamedQuery(name = "Seleccionaplicante.findByActivo", query = "SELECT s FROM Seleccionaplicante s WHERE s.activo = :activo")})
public class Seleccionaplicante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idseleccionaplicante")
    private Integer idseleccionaplicante;
    @Basic(optional = false)
    @Column(name = "fechaseleccion")
    @Temporal(TemporalType.DATE)
    private Date fechaseleccion;
    @Basic(optional = false)
    @Column(name = "horaseleccion")
    @Temporal(TemporalType.TIME)
    private Date horaseleccion;
    @Basic(optional = false)
    @Column(name = "comentarios")
    private String comentarios;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "aplicacionespasantias_idaplicacionpasantias", referencedColumnName = "idaplicacionpasantias")
    @ManyToOne(fetch = FetchType.LAZY)
    private Aplicacionespasantias aplicacionespasantiasIdaplicacionpasantias;
    @JoinColumn(name = "usuariosinstituciones_idusuariosinstituciones", referencedColumnName = "idusuariosinstituciones")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuariosinstituciones usuariosinstitucionesIdusuariosinstituciones;

    public Seleccionaplicante() {
    }

    public Seleccionaplicante(Integer idseleccionaplicante) {
        this.idseleccionaplicante = idseleccionaplicante;
    }

    public Seleccionaplicante(Integer idseleccionaplicante, Date fechaseleccion, Date horaseleccion, String comentarios, boolean activo) {
        this.idseleccionaplicante = idseleccionaplicante;
        this.fechaseleccion = fechaseleccion;
        this.horaseleccion = horaseleccion;
        this.comentarios = comentarios;
        this.activo = activo;
    }

    public Integer getIdseleccionaplicante() {
        return idseleccionaplicante;
    }

    public void setIdseleccionaplicante(Integer idseleccionaplicante) {
        this.idseleccionaplicante = idseleccionaplicante;
    }

    public Date getFechaseleccion() {
        return fechaseleccion;
    }

    public void setFechaseleccion(Date fechaseleccion) {
        this.fechaseleccion = fechaseleccion;
    }

    public Date getHoraseleccion() {
        return horaseleccion;
    }

    public void setHoraseleccion(Date horaseleccion) {
        this.horaseleccion = horaseleccion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Aplicacionespasantias getAplicacionespasantiasIdaplicacionpasantias() {
        return aplicacionespasantiasIdaplicacionpasantias;
    }

    public void setAplicacionespasantiasIdaplicacionpasantias(Aplicacionespasantias aplicacionespasantiasIdaplicacionpasantias) {
        this.aplicacionespasantiasIdaplicacionpasantias = aplicacionespasantiasIdaplicacionpasantias;
    }

    public Usuariosinstituciones getUsuariosinstitucionesIdusuariosinstituciones() {
        return usuariosinstitucionesIdusuariosinstituciones;
    }

    public void setUsuariosinstitucionesIdusuariosinstituciones(Usuariosinstituciones usuariosinstitucionesIdusuariosinstituciones) {
        this.usuariosinstitucionesIdusuariosinstituciones = usuariosinstitucionesIdusuariosinstituciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idseleccionaplicante != null ? idseleccionaplicante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seleccionaplicante)) {
            return false;
        }
        Seleccionaplicante other = (Seleccionaplicante) object;
        if ((this.idseleccionaplicante == null && other.idseleccionaplicante != null) || (this.idseleccionaplicante != null && !this.idseleccionaplicante.equals(other.idseleccionaplicante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Seleccionaplicante[ idseleccionaplicante=" + idseleccionaplicante + " ]";
    }
    
}
