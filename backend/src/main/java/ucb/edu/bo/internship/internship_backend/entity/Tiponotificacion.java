/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "tiponotificacion")
@NamedQueries({
    @NamedQuery(name = "Tiponotificacion.findAll", query = "SELECT t FROM Tiponotificacion t"),
    @NamedQuery(name = "Tiponotificacion.findByIdtiponotificacion", query = "SELECT t FROM Tiponotificacion t WHERE t.idtiponotificacion = :idtiponotificacion"),
    @NamedQuery(name = "Tiponotificacion.findByNombrenotificacion", query = "SELECT t FROM Tiponotificacion t WHERE t.nombrenotificacion = :nombrenotificacion")})
public class Tiponotificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtiponotificacion")
    private Integer idtiponotificacion;
    @Basic(optional = false)
    @Column(name = "nombrenotificacion")
    private String nombrenotificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiponotificacionIdtiponotificacion", fetch = FetchType.LAZY)
    private List<Notificaciones> notificacionesList;

    public Tiponotificacion() {
    }

    public Tiponotificacion(Integer idtiponotificacion) {
        this.idtiponotificacion = idtiponotificacion;
    }

    public Tiponotificacion(Integer idtiponotificacion, String nombrenotificacion) {
        this.idtiponotificacion = idtiponotificacion;
        this.nombrenotificacion = nombrenotificacion;
    }

    public Integer getIdtiponotificacion() {
        return idtiponotificacion;
    }

    public void setIdtiponotificacion(Integer idtiponotificacion) {
        this.idtiponotificacion = idtiponotificacion;
    }

    public String getNombrenotificacion() {
        return nombrenotificacion;
    }

    public void setNombrenotificacion(String nombrenotificacion) {
        this.nombrenotificacion = nombrenotificacion;
    }

    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtiponotificacion != null ? idtiponotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiponotificacion)) {
            return false;
        }
        Tiponotificacion other = (Tiponotificacion) object;
        if ((this.idtiponotificacion == null && other.idtiponotificacion != null) || (this.idtiponotificacion != null && !this.idtiponotificacion.equals(other.idtiponotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Tiponotificacion[ idtiponotificacion=" + idtiponotificacion + " ]";
    }
    
}
