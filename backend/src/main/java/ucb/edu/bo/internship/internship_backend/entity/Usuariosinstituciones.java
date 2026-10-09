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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "usuariosinstituciones")
@NamedQueries({
    @NamedQuery(name = "Usuariosinstituciones.findAll", query = "SELECT u FROM Usuariosinstituciones u"),
    @NamedQuery(name = "Usuariosinstituciones.findByIdusuariosinstituciones", query = "SELECT u FROM Usuariosinstituciones u WHERE u.idusuariosinstituciones = :idusuariosinstituciones"),
    @NamedQuery(name = "Usuariosinstituciones.findByCargo", query = "SELECT u FROM Usuariosinstituciones u WHERE u.cargo = :cargo")})
public class Usuariosinstituciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuariosinstituciones")
    private Integer idusuariosinstituciones;
    @Basic(optional = false)
    @Column(name = "cargo")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "instituciones_idinstituciones", referencedColumnName = "idinstituciones")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Instituciones institucionesIdinstituciones;
    @JoinColumn(name = "usuarios_idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios usuariosIdusuarios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosinstitucionesIdusuariosinstituciones", fetch = FetchType.LAZY)
    private List<Seleccionaplicante> seleccionaplicanteList;

    public Usuariosinstituciones() {
    }

    public Usuariosinstituciones(Integer idusuariosinstituciones) {
        this.idusuariosinstituciones = idusuariosinstituciones;
    }

    public Usuariosinstituciones(Integer idusuariosinstituciones, String cargo,Boolean activo) {
        this.idusuariosinstituciones = idusuariosinstituciones;
        this.cargo = cargo;
        this.activo = activo;
    }

    public Integer getIdusuariosinstituciones() {
        return idusuariosinstituciones;
    }

    public void setIdusuariosinstituciones(Integer idusuariosinstituciones) {
        this.idusuariosinstituciones = idusuariosinstituciones;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Instituciones getInstitucionesIdinstituciones() {
        return institucionesIdinstituciones;
    }

    public void setInstitucionesIdinstituciones(Instituciones institucionesIdinstituciones) {
        this.institucionesIdinstituciones = institucionesIdinstituciones;
    }

    public Usuarios getUsuariosIdusuarios() {
        return usuariosIdusuarios;
    }

    public void setUsuariosIdusuarios(Usuarios usuariosIdusuarios) {
        this.usuariosIdusuarios = usuariosIdusuarios;
    }

    public List<Seleccionaplicante> getSeleccionaplicanteList() {
        return seleccionaplicanteList;
    }

    public void setSeleccionaplicanteList(List<Seleccionaplicante> seleccionaplicanteList) {
        this.seleccionaplicanteList = seleccionaplicanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuariosinstituciones != null ? idusuariosinstituciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariosinstituciones)) {
            return false;
        }
        Usuariosinstituciones other = (Usuariosinstituciones) object;
        if ((this.idusuariosinstituciones == null && other.idusuariosinstituciones != null) || (this.idusuariosinstituciones != null && !this.idusuariosinstituciones.equals(other.idusuariosinstituciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Usuariosinstituciones[ idusuariosinstituciones=" + idusuariosinstituciones + " ]";
    }
    
}
