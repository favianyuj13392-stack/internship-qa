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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "pasantias")
@NamedQueries({
    @NamedQuery(name = "Pasantias.findAll", query = "SELECT p FROM Pasantias p"),
    @NamedQuery(name = "Pasantias.findByIdpasantias", query = "SELECT p FROM Pasantias p WHERE p.idpasantias = :idpasantias"),
    @NamedQuery(name = "Pasantias.findByTitulo", query = "SELECT p FROM Pasantias p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Pasantias.findByDescripcion", query = "SELECT p FROM Pasantias p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Pasantias.findByFechacierre", query = "SELECT p FROM Pasantias p WHERE p.fechacierre = :fechacierre"),
    @NamedQuery(name = "Pasantias.findByFechaingreso", query = "SELECT p FROM Pasantias p WHERE p.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Pasantias.findByActivo", query = "SELECT p FROM Pasantias p WHERE p.activo = :activo")})
public class Pasantias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasantias")
    private Integer idpasantias;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "areas")
    private List<String> areas;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "descripcion" , length = 1000)
    private String descripcion;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "requisitos")
    private List<String> requisitos;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "funciones")
    private List<String> funciones;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "beneficios")
    private List<String> beneficios;
    @Basic(optional = false)
    @Column(name = "fechacierre")
    @Temporal(TemporalType.DATE)
    private Date fechacierre;
    @Basic(optional = false)
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = true)
    @Column(name="sinaplicantes")
    private Boolean sinaplicantes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasantiasIdpasantias", fetch = FetchType.LAZY)
    private List<Aplicacionespasantias> aplicacionespasantiasList;



    @JoinColumn(name = "instituciones_idinstituciones", referencedColumnName = "idinstituciones")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Instituciones institucionesIdinstituciones;

    @JoinColumn(name = "usuarios_idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private Usuarios usuariosIdusuarios;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasantiasIdpasantias", fetch = FetchType.LAZY)
    private List<Pasantiascarreras> pasantiascarrerasList;



    public Pasantias() {
    }

    public Pasantias(Integer idpasantias) {
        this.idpasantias = idpasantias;
    }

    public Pasantias(Integer idpasantias, List<String> areas, String titulo, String descripcion, List<String> requisitos, List<String> funciones, List<String> beneficios, Date fechacierre, Date fechaingreso, boolean activo, Boolean sinaplicantes) {
        this.idpasantias = idpasantias;
        this.areas = areas;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.funciones = funciones;
        this.beneficios = beneficios;
        this.fechacierre = fechacierre;
        this.fechaingreso = fechaingreso;
        this.activo = activo;
        this.sinaplicantes = sinaplicantes;
    }

    public Integer getIdpasantias() {
        return idpasantias;
    }

    public Usuarios getUsuariosIdusuarios() {
        return usuariosIdusuarios;
    }

    public void setUsuariosIdusuarios(Usuarios usuariosIdusuarios) {
        this.usuariosIdusuarios = usuariosIdusuarios;
    }

    public void setIdpasantias(Integer idpasantias) {
        this.idpasantias = idpasantias;
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<String> requisitos) {
        this.requisitos = requisitos;
    }

    public List<String> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<String> funciones) {
        this.funciones = funciones;
    }

    public List<String> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<String> beneficios) {
        this.beneficios = beneficios;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Boolean isSinaplicantes() {
        return sinaplicantes;
    }

    public void setSinaplicantes(Boolean sinaplicantes) {
        this.sinaplicantes = sinaplicantes;
    }

    public List<Aplicacionespasantias> getAplicacionespasantiasList() {
        return aplicacionespasantiasList;
    }

    public void setAplicacionespasantiasList(List<Aplicacionespasantias> aplicacionespasantiasList) {
        this.aplicacionespasantiasList = aplicacionespasantiasList;
    }

    public Instituciones getInstitucionesIdinstituciones() {
        return institucionesIdinstituciones;
    }

    public void setInstitucionesIdinstituciones(Instituciones institucionesIdinstituciones) {
        this.institucionesIdinstituciones = institucionesIdinstituciones;
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
        hash += (idpasantias != null ? idpasantias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasantias)) {
            return false;
        }
        Pasantias other = (Pasantias) object;
        if ((this.idpasantias == null && other.idpasantias != null) || (this.idpasantias != null && !this.idpasantias.equals(other.idpasantias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Pasantias[ idpasantias=" + idpasantias + " ]";
    }
    
}
