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
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ucb.edu.bo.internship.internship_backend.dto.InstitucionesDto;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "instituciones")
@NamedQueries({
    @NamedQuery(name = "Instituciones.findAll", query = "SELECT i FROM Instituciones i"),
    @NamedQuery(name = "Instituciones.findByIdinstituciones", query = "SELECT i FROM Instituciones i WHERE i.idinstituciones = :idinstituciones"),
    @NamedQuery(name = "Instituciones.findByNombre", query = "SELECT i FROM Instituciones i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Instituciones.findByDescripcion", query = "SELECT i FROM Instituciones i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Instituciones.findByDireccion", query = "SELECT i FROM Instituciones i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Instituciones.findByFotoinstitucion", query = "SELECT i FROM Instituciones i WHERE i.fotoinstitucion = :fotoinstitucion"),
    @NamedQuery(name = "Instituciones.findByCorreo", query = "SELECT i FROM Instituciones i WHERE i.correo = :correo"),
    @NamedQuery(name = "Instituciones.findByLogoempresa", query = "SELECT i FROM Instituciones i WHERE i.logoempresa = :logoempresa"),
    @NamedQuery(name = "Instituciones.findByActivo", query = "SELECT i FROM Instituciones i WHERE i.activo = :activo")
})
public class Instituciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinstituciones")
    private Integer idinstituciones;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "fotoinstitucion")
    private String fotoinstitucion;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sectores")
    private List<String> sectores;
    @Basic(optional = false)
    @Column(name = "logoempresa")
    private String logoempresa;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "fotos")
    private List<String> fotos;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "redessociales")
    private LinkedHashMap<String,String> redessociales;
    @Basic(optional = false)
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institucionesIdinstituciones", fetch = FetchType.LAZY)
    private List<Usuariosinstituciones> usuariosinstitucionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institucionesIdinstituciones", fetch = FetchType.LAZY)
    private List<Pasantias> pasantiasList;

    public Instituciones() {
    }

    public Instituciones(Integer idinstituciones) {
        this.idinstituciones = idinstituciones;
    }

    public Instituciones(Integer idinstituciones, String nombre, String descripcion, String direccion, String fotoinstitucion, String correo, List<String> sectores, String logoempresa, List<String> fotos, LinkedHashMap<String,String> redessociales, Boolean activo) {
        this.idinstituciones = idinstituciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.fotoinstitucion = fotoinstitucion;
        this.correo = correo;
        this.sectores = sectores;
        this.logoempresa = logoempresa;
        this.fotos = fotos;
        this.redessociales = redessociales;
        this.activo = activo;
    }
    public Instituciones(InstitucionesDto institucionesDto){
        this.idinstituciones = institucionesDto.getIdInstituciones();
        this.nombre = institucionesDto.getNombre();
        this.descripcion = institucionesDto.getDescripcion();
        this.direccion = institucionesDto.getDireccion();
        this.fotoinstitucion = institucionesDto.getFotoInstitucion();
        this.correo = institucionesDto.getCorreo();
        this.sectores = institucionesDto.getSectores();
        this.logoempresa = institucionesDto.getLogoEmpresa();
        this.fotos = institucionesDto.getFotos();
        this.redessociales = institucionesDto.getRedesSociales();
        this.activo = institucionesDto.getActivo();
    }

    public Integer getIdinstituciones() {
        return idinstituciones;
    }

    public void setIdinstituciones(Integer idinstituciones) {
        this.idinstituciones = idinstituciones;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoinstitucion() {
        return fotoinstitucion;
    }

    public void setFotoinstitucion(String fotoinstitucion) {
        this.fotoinstitucion = fotoinstitucion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<String> getSectores() {
        return sectores;
    }

    public void setSectores(List<String> sectores) {
        this.sectores = sectores;
    }

    public String getLogoempresa() {
        return logoempresa;
    }

    public void setLogoempresa(String logoempresa) {
        this.logoempresa = logoempresa;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public LinkedHashMap<String,String> getRedessociales() {
        return redessociales;
    }

    public void setRedessociales(LinkedHashMap<String,String> redessociales) {
        this.redessociales = redessociales;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Usuariosinstituciones> getUsuariosinstitucionesList() {
        return usuariosinstitucionesList;
    }

    public void setUsuariosinstitucionesList(List<Usuariosinstituciones> usuariosinstitucionesList) {
        this.usuariosinstitucionesList = usuariosinstitucionesList;
    }

    public List<Pasantias> getPasantiasList() {
        return pasantiasList;
    }

    public void setPasantiasList(List<Pasantias> pasantiasList) {
        this.pasantiasList = pasantiasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinstituciones != null ? idinstituciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instituciones)) {
            return false;
        }
        Instituciones other = (Instituciones) object;
        if ((this.idinstituciones == null && other.idinstituciones != null) || (this.idinstituciones != null && !this.idinstituciones.equals(other.idinstituciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Instituciones[ idinstituciones=" + idinstituciones + " ]";
    }
    
}
