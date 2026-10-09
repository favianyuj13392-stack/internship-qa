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
@Table(name = "roles")
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByIdroles", query = "SELECT r FROM Roles r WHERE r.idroles = :idroles"),
    @NamedQuery(name = "Roles.findByRol", query = "SELECT r FROM Roles r WHERE r.rol = :rol"),
    @NamedQuery(name = "Roles.findByDesripcion", query = "SELECT r FROM Roles r WHERE r.desripcion = :desripcion")})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idroles")
    private Integer idroles;
    @Basic(optional = false)
    @Column(name = "rol")
    private String rol;
    @Basic(optional = false)
    @Column(name = "desripcion")
    private String desripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolesIdroles", fetch = FetchType.LAZY)
    private List<Usuarios> usuariosList;

    public Roles() {
    }

    public Roles(Integer idroles) {
        this.idroles = idroles;
    }

    public Roles(Integer idroles, String rol, String desripcion) {
        this.idroles = idroles;
        this.rol = rol;
        this.desripcion = desripcion;
    }

    public Integer getIdroles() {
        return idroles;
    }

    public void setIdroles(Integer idroles) {
        this.idroles = idroles;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDesripcion() {
        return desripcion;
    }

    public void setDesripcion(String desripcion) {
        this.desripcion = desripcion;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idroles != null ? idroles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.idroles == null && other.idroles != null) || (this.idroles != null && !this.idroles.equals(other.idroles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Roles[ idroles=" + idroles + " ]";
    }
    
}
