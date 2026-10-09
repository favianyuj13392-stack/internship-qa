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

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdusuarios", query = "SELECT u FROM Usuarios u WHERE u.idusuarios = :idusuarios"),
    @NamedQuery(name = "Usuarios.findByKcUuid", query = "SELECT u FROM Usuarios u WHERE u.kcUuid = :kcUuid"),
    @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuarios.findByFecharegistro", query = "SELECT u FROM Usuarios u WHERE u.fecharegistro = :fecharegistro"),
    @NamedQuery(name = "Usuarios.findByHoraregistro", query = "SELECT u FROM Usuarios u WHERE u.horaregistro = :horaregistro"),
    @NamedQuery(name = "Usuarios.findByActivo", query = "SELECT u FROM Usuarios u WHERE u.activo = :activo")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuarios")
    private Integer idusuarios;
    @Basic(optional = false)
    @Column(name = "kc_uuid")
    private String kcUuid;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "fecharegistro")
    @Temporal(TemporalType.DATE)
    private Date fecharegistro;
    @Basic(optional = false)
    @Column(name = "horaregistro")
    @Temporal(TemporalType.TIME)
    private Date horaregistro;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosIdusuarios", fetch = FetchType.LAZY)
    private List<Aplicacionespasantias> aplicacionespasantiasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosIdusuarios", fetch = FetchType.LAZY)
    private List<Usuariosinstituciones> usuariosinstitucionesList;
    @JoinColumn(name = "carreras_idcarreras", referencedColumnName = "idcarreras")
    @ManyToOne(fetch = FetchType.LAZY)
    private Carreras carrerasIdcarreras;
    @JoinColumn(name = "personas_idpersonas", referencedColumnName = "idpersonas")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Personas personasIdpersonas;
    @JoinColumn(name = "roles_idroles", referencedColumnName = "idroles")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Roles rolesIdroles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosIdusuarios", fetch = FetchType.LAZY)
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosIdusuarios", fetch = FetchType.LAZY)
    private List<Curriculums> curriculumsList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosIdusuarios", fetch = FetchType.LAZY)
    private List<Pasantias> pasantiasList;

    public Usuarios() {
    }

    public Usuarios(Integer idusuarios) {
        this.idusuarios = idusuarios;
    }

    public Usuarios(Integer idusuarios, String kcUuid, String correo, Date fecharegistro, Date horaregistro, boolean activo) {
        this.idusuarios = idusuarios;
        this.kcUuid = kcUuid;
        this.correo = correo;
        this.fecharegistro = fecharegistro;
        this.horaregistro = horaregistro;
        this.activo = activo;
    }

    public Integer getIdusuarios() {
        return idusuarios;
    }

    public void setIdusuarios(Integer idusuarios) {
        this.idusuarios = idusuarios;
    }

    public String getKcUuid() {
        return kcUuid;
    }

    public void setKcUuid(String kcUuid) {
        this.kcUuid = kcUuid;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

   

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Date getHoraregistro() {
        return horaregistro;
    }

    public void setHoraregistro(Date horaregistro) {
        this.horaregistro = horaregistro;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Aplicacionespasantias> getAplicacionespasantiasList() {
        return aplicacionespasantiasList;
    }

    public void setAplicacionespasantiasList(List<Aplicacionespasantias> aplicacionespasantiasList) {
        this.aplicacionespasantiasList = aplicacionespasantiasList;
    }

    public List<Usuariosinstituciones> getUsuariosinstitucionesList() {
        return usuariosinstitucionesList;
    }

    public void setUsuariosinstitucionesList(List<Usuariosinstituciones> usuariosinstitucionesList) {
        this.usuariosinstitucionesList = usuariosinstitucionesList;
    }

    public Carreras getCarrerasIdcarreras() {
        return carrerasIdcarreras;
    }

    public void setCarrerasIdcarreras(Carreras carrerasIdcarreras) {
        this.carrerasIdcarreras = carrerasIdcarreras;
    }

    public Personas getPersonasIdpersonas() {
        return personasIdpersonas;
    }

    public void setPersonasIdpersonas(Personas personasIdpersonas) {
        this.personasIdpersonas = personasIdpersonas;
    }

    public Roles getRolesIdroles() {
        return rolesIdroles;
    }

    public void setRolesIdroles(Roles rolesIdroles) {
        this.rolesIdroles = rolesIdroles;
    }

    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    public List<Curriculums> getCurriculumsList() {
        return curriculumsList;
    }

    public void setCurriculumsList(List<Curriculums> curriculumsList) {
        this.curriculumsList = curriculumsList;
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
        hash += (idusuarios != null ? idusuarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idusuarios == null && other.idusuarios != null) || (this.idusuarios != null && !this.idusuarios.equals(other.idusuarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Usuarios[ idusuarios=" + idusuarios + " ]";
    }
    
}
