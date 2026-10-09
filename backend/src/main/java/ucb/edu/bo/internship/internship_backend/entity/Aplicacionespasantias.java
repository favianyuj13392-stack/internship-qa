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
@Table(name = "aplicacionespasantias")
@NamedQueries({
    @NamedQuery(name = "Aplicacionespasantias.findAll", query = "SELECT a FROM Aplicacionespasantias a"),
    @NamedQuery(name = "Aplicacionespasantias.findByIdaplicacionpasantias", query = "SELECT a FROM Aplicacionespasantias a WHERE a.idaplicacionpasantias = :idaplicacionpasantias"),
    @NamedQuery(name = "Aplicacionespasantias.findByFechaaplicacion", query = "SELECT a FROM Aplicacionespasantias a WHERE a.fechaaplicacion = :fechaaplicacion"),
    @NamedQuery(name = "Aplicacionespasantias.findByActivo", query = "SELECT a FROM Aplicacionespasantias a WHERE a.activo = :activo")})
public class Aplicacionespasantias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaplicacionpasantias")
    private Integer idaplicacionpasantias;
    @Basic(optional = false)
    @Column(name = "fechaaplicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaaplicacion;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "pasantias_idpasantias", referencedColumnName = "idpasantias")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pasantias pasantiasIdpasantias;
    @JoinColumn(name = "usuarios_idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios usuariosIdusuarios;
    @JoinColumn(name = "curriculums_idcurriculums", referencedColumnName = "idcurriculums")
    @ManyToOne(optional = true, fetch =FetchType.LAZY)
    private Curriculums curriculumsIdcurriculums;
    @OneToMany(mappedBy = "aplicacionespasantiasIdaplicacionpasantias", fetch = FetchType.LAZY)
    private List<Seleccionaplicante> seleccionaplicanteList;

    public Aplicacionespasantias() {
    }

    public Aplicacionespasantias(Integer idaplicacionpasantias) {
        this.idaplicacionpasantias = idaplicacionpasantias;
    }

    public Aplicacionespasantias(Integer idaplicacionpasantias, Date fechaaplicacion, boolean activo) {
        this.idaplicacionpasantias = idaplicacionpasantias;
        this.fechaaplicacion = fechaaplicacion;
        this.activo = activo;
    }

    public Integer getIdaplicacionpasantias() {
        return idaplicacionpasantias;
    }

    public void setIdaplicacionpasantias(Integer idaplicacionpasantias) {
        this.idaplicacionpasantias = idaplicacionpasantias;
    }

    public Date getFechaaplicacion() {
        return fechaaplicacion;
    }

    public void setFechaaplicacion(Date fechaaplicacion) {
        this.fechaaplicacion = fechaaplicacion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Pasantias getPasantiasIdpasantias() {
        return pasantiasIdpasantias;
    }

    public void setPasantiasIdpasantias(Pasantias pasantiasIdpasantias) {
        this.pasantiasIdpasantias = pasantiasIdpasantias;
    }

    public Usuarios getUsuariosIdusuarios() {
        return usuariosIdusuarios;
    }

    public void setUsuariosIdusuarios(Usuarios usuariosIdusuarios) {
        this.usuariosIdusuarios = usuariosIdusuarios;
    }

    public Curriculums getCurriculumsIdcurriculums() {
        return curriculumsIdcurriculums;
    }

    public void setCurriculumsIdcurriculums(Curriculums curriculumsIdcurriculums) {
        this.curriculumsIdcurriculums = curriculumsIdcurriculums;
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
        hash += (idaplicacionpasantias != null ? idaplicacionpasantias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aplicacionespasantias)) {
            return false;
        }
        Aplicacionespasantias other = (Aplicacionespasantias) object;
        if ((this.idaplicacionpasantias == null && other.idaplicacionpasantias != null) || (this.idaplicacionpasantias != null && !this.idaplicacionpasantias.equals(other.idaplicacionpasantias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Aplicacionespasantias[ idaplicacionpasantias=" + idaplicacionpasantias + " ]";
    }
    
}
