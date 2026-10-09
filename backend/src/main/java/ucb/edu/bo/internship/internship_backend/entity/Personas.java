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
@Table(name = "personas")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByIdpersonas", query = "SELECT p FROM Personas p WHERE p.idpersonas = :idpersonas"),
    @NamedQuery(name = "Personas.findByNombres", query = "SELECT p FROM Personas p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Personas.findByApellidopaterno", query = "SELECT p FROM Personas p WHERE p.apellidopaterno = :apellidopaterno"),
    @NamedQuery(name = "Personas.findByApellidomaterno", query = "SELECT p FROM Personas p WHERE p.apellidomaterno = :apellidomaterno"),
    @NamedQuery(name = "Personas.findByTelefono", query = "SELECT p FROM Personas p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Personas.findByCi", query = "SELECT p FROM Personas p WHERE p.ci = :ci"),
    @NamedQuery(name = "Personas.findByFotoperfil", query = "SELECT p FROM Personas p WHERE p.fotoperfil = :fotoperfil"),
    @NamedQuery(name = "Personas.findByAnioingresouniversidad", query = "SELECT p FROM Personas p WHERE p.anioingresouniversidad = :anioingresouniversidad"),
    @NamedQuery(name = "Personas.findByDescripcion", query = "SELECT p FROM Personas p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Personas.findByFechadenacimiento", query = "SELECT p FROM Personas p WHERE p.fechadenacimiento = :fechadenacimiento")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersonas")
    private Integer idpersonas;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidopaterno")
    private String apellidopaterno;
    @Basic(optional = false)
    @Column(name = "apellidomaterno")
    private String apellidomaterno;
    @Basic(optional = false)
    @Column(name = "telefono")
    private int telefono;
    @Basic(optional = false)
    @Column(name = "ci")
    private String ci;
    @Basic(optional = false)
    @Column(name = "fotoperfil")
    private String fotoperfil;

    @Column(name = "bannerperfil")
    private String bannerperfil;

    @Column(name = "anioingresouniversidad")
    private Integer anioingresouniversidad;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "habilidades")
    private Object habilidades;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "habilidadesseleccionadas")
    private Object habilidadesseleccionadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "experiencia")
    private Object experiencia;
    @Column(name = "fechadenacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechadenacimiento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "redessociales")
    private Object redessociales;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personasIdpersonas", fetch = FetchType.LAZY)
    private List<Usuarios> usuariosList;

    public Personas() {
    }

    public Personas(Integer idpersonas) {
        this.idpersonas = idpersonas;
    }

    public Personas(Integer idpersonas, String nombres, String apellidopaterno, String apellidomaterno, int telefono, String ci, String fotoperfil, String bannerperfil) {
        this.idpersonas = idpersonas;
        this.nombres = nombres;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.telefono = telefono;
        this.ci = ci;
        this.fotoperfil = fotoperfil;
        this.bannerperfil = bannerperfil;
    }

    public Integer getIdpersonas() {
        return idpersonas;
    }

    public void setIdpersonas(Integer idpersonas) {
        this.idpersonas = idpersonas;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public String getBannerperfil() {
        return bannerperfil;
    }

    public void setBannerperfil(String bannerperfil) {
        this.bannerperfil = bannerperfil;
    }

    public Integer getAnioingresouniversidad() {
        return anioingresouniversidad;
    }

    public void setAnioingresouniversidad(Integer anioingresouniversidad) {
        this.anioingresouniversidad = anioingresouniversidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Object habilidades) {
        this.habilidades = habilidades;
    }

    public Object getHabilidadesseleccionadas() {
        return habilidadesseleccionadas;
    }

    public void setHabilidadesseleccionadas(Object habilidadesseleccionadas) {
        this.habilidadesseleccionadas = habilidadesseleccionadas;
    }

    public Object getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Object experiencia) {
        this.experiencia = experiencia;
    }

    public Date getFechadenacimiento() {
        return fechadenacimiento;
    }

    public void setFechadenacimiento(Date fechadenacimiento) {
        this.fechadenacimiento = fechadenacimiento;
    }

    public Object getRedessociales() {
        return redessociales;
    }

    public void setRedessociales(Object redessociales) {
        this.redessociales = redessociales;
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
        hash += (idpersonas != null ? idpersonas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.idpersonas == null && other.idpersonas != null) || (this.idpersonas != null && !this.idpersonas.equals(other.idpersonas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Personas[ idpersonas=" + idpersonas + " ]";
    }
    
}
