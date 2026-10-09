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
@Table(name = "notificaciones")
@NamedQueries({
    @NamedQuery(name = "Notificaciones.findAll", query = "SELECT n FROM Notificaciones n"),
    @NamedQuery(name = "Notificaciones.findByIdnotificacion", query = "SELECT n FROM Notificaciones n WHERE n.idnotificacion = :idnotificacion"),
    @NamedQuery(name = "Notificaciones.findByMensaje", query = "SELECT n FROM Notificaciones n WHERE n.mensaje = :mensaje"),
    @NamedQuery(name = "Notificaciones.findByNombreentidad", query = "SELECT n FROM Notificaciones n WHERE n.nombreentidad = :nombreentidad"),
    @NamedQuery(name = "Notificaciones.findByIdentidad", query = "SELECT n FROM Notificaciones n WHERE n.identidad = :identidad"),
    @NamedQuery(name = "Notificaciones.findByFechaenvio", query = "SELECT n FROM Notificaciones n WHERE n.fechaenvio = :fechaenvio"),
    @NamedQuery(name = "Notificaciones.findByLeido", query = "SELECT n FROM Notificaciones n WHERE n.leido = :leido")})
public class Notificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotificacion")
    private Integer idnotificacion;
    @Basic(optional = false)
    @Column(name = "mensaje")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "nombreentidad")
    private String nombreentidad;
    @Basic(optional = false)
    @Column(name = "identidad")
    private int identidad;
    @Basic(optional = false)
    @Column(name = "fechaenvio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaenvio;
    @Basic(optional = false)
    @Column(name = "leido")
    private boolean leido;
    @JoinColumn(name = "tiponotificacion_idtiponotificacion", referencedColumnName = "idtiponotificacion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tiponotificacion tiponotificacionIdtiponotificacion;
    @JoinColumn(name = "usuarios_idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios usuariosIdusuarios;

    public Notificaciones() {
    }

    public Notificaciones(Integer idnotificacion) {
        this.idnotificacion = idnotificacion;
    }

    public Notificaciones(Integer idnotificacion, String mensaje, String nombreentidad, int identidad, Date fechaenvio, boolean leido) {
        this.idnotificacion = idnotificacion;
        this.mensaje = mensaje;
        this.nombreentidad = nombreentidad;
        this.identidad = identidad;
        this.fechaenvio = fechaenvio;
        this.leido = leido;
    }

    public Integer getIdnotificacion() {
        return idnotificacion;
    }

    public void setIdnotificacion(Integer idnotificacion) {
        this.idnotificacion = idnotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreentidad() {
        return nombreentidad;
    }

    public void setNombreentidad(String nombreentidad) {
        this.nombreentidad = nombreentidad;
    }

    public int getIdentidad() {
        return identidad;
    }

    public void setIdentidad(int identidad) {
        this.identidad = identidad;
    }

    public Date getFechaenvio() {
        return fechaenvio;
    }

    public void setFechaenvio(Date fechaenvio) {
        this.fechaenvio = fechaenvio;
    }

    public boolean getLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public Tiponotificacion getTiponotificacionIdtiponotificacion() {
        return tiponotificacionIdtiponotificacion;
    }

    public void setTiponotificacionIdtiponotificacion(Tiponotificacion tiponotificacionIdtiponotificacion) {
        this.tiponotificacionIdtiponotificacion = tiponotificacionIdtiponotificacion;
    }

    public Usuarios getUsuariosIdusuarios() {
        return usuariosIdusuarios;
    }

    public void setUsuariosIdusuarios(Usuarios usuariosIdusuarios) {
        this.usuariosIdusuarios = usuariosIdusuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotificacion != null ? idnotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificaciones)) {
            return false;
        }
        Notificaciones other = (Notificaciones) object;
        if ((this.idnotificacion == null && other.idnotificacion != null) || (this.idnotificacion != null && !this.idnotificacion.equals(other.idnotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Notificaciones[ idnotificacion=" + idnotificacion + " ]";
    }
    
}
