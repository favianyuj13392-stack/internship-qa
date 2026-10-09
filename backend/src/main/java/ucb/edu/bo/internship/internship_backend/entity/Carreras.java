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
@Table(name = "carreras")
@NamedQueries({
    @NamedQuery(name = "Carreras.findAll", query = "SELECT c FROM Carreras c"),
    @NamedQuery(name = "Carreras.findByIdcarreras", query = "SELECT c FROM Carreras c WHERE c.idcarreras = :idcarreras"),
    @NamedQuery(name = "Carreras.findByNombre", query = "SELECT c FROM Carreras c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Carreras.findByDescripcion", query = "SELECT c FROM Carreras c WHERE c.descripcion = :descripcion")})
public class Carreras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcarreras")
    private Integer idcarreras;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "carrerasIdcarreras", fetch = FetchType.LAZY)
    private List<Usuarios> usuariosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carrerasIdcarreras", fetch = FetchType.LAZY)
    private List<Pasantiascarreras> pasantiascarrerasList;

    public Carreras() {
    }

    public Carreras(Integer idcarreras) {
        this.idcarreras = idcarreras;
    }

    public Carreras(Integer idcarreras, String nombre, String descripcion) {
        this.idcarreras = idcarreras;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdcarreras() {
        return idcarreras;
    }

    public void setIdcarreras(Integer idcarreras) {
        this.idcarreras = idcarreras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public List<Pasantiascarreras> getPasantiascarrerasList() {
        return pasantiascarrerasList;
    }

    public void setPasantiascarrerasList(List<Pasantiascarreras> pasantiascarrerasList) {
        this.pasantiascarrerasList = pasantiascarrerasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcarreras != null ? idcarreras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carreras)) {
            return false;
        }
        Carreras other = (Carreras) object;
        if ((this.idcarreras == null && other.idcarreras != null) || (this.idcarreras != null && !this.idcarreras.equals(other.idcarreras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Carreras[ idcarreras=" + idcarreras + " ]";
    }
    
}
