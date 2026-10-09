/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "curriculums")
@NamedQueries({
    @NamedQuery(name = "Curriculums.findAll", query = "SELECT c FROM Curriculums c"),
    @NamedQuery(name = "Curriculums.findByIdcurriculums", query = "SELECT c FROM Curriculums c WHERE c.idcurriculums = :idcurriculums"),
    @NamedQuery(name = "Curriculums.findByFechacargado", query = "SELECT c FROM Curriculums c WHERE c.fechacargado = :fechacargado"),
    @NamedQuery(name = "Curriculums.findByTitulo", query = "SELECT c FROM Curriculums c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "Curriculums.findByPdfcurriculum", query = "SELECT c FROM Curriculums c WHERE c.pdfcurriculum = :pdfcurriculum")})
public class Curriculums implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcurriculums")
    private Integer idcurriculums;
    @Basic(optional = false)
    @Column(name = "fechacargado")
    @Temporal(TemporalType.DATE)
    private Date fechacargado;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "pdfcurriculum")
    private String pdfcurriculum;
    @JoinColumn(name = "usuarios_idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuarios usuariosIdusuarios;
    @OneToMany(mappedBy = "curriculumsIdcurriculums", fetch = FetchType.LAZY)
    private List<Aplicacionespasantias> aplicacionespasantiasList;

    public Curriculums() {
    }

    public Curriculums(Integer idcurriculums) {
        this.idcurriculums = idcurriculums;
    }

    public Curriculums(Integer idcurriculums, Date fechacargado, String titulo, String pdfcurriculum) {
        this.idcurriculums = idcurriculums;
        this.fechacargado = fechacargado;
        this.titulo = titulo;
        this.pdfcurriculum = pdfcurriculum;
    }

    public Integer getIdcurriculums() {
        return idcurriculums;
    }

    public void setIdcurriculums(Integer idcurriculums) {
        this.idcurriculums = idcurriculums;
    }

    public Date getFechacargado() {
        return fechacargado;
    }

    public void setFechacargado(Date fechacargado) {
        this.fechacargado = fechacargado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPdfcurriculum() {
        return pdfcurriculum;
    }

    public void setPdfcurriculum(String pdfcurriculum) {
        this.pdfcurriculum = pdfcurriculum;
    }

    public Usuarios getUsuariosIdusuarios() {
        return usuariosIdusuarios;
    }

    public void setUsuariosIdusuarios(Usuarios usuariosIdusuarios) {
        this.usuariosIdusuarios = usuariosIdusuarios;
    }

    public List<Aplicacionespasantias> getAplicacionespasantiasList() {
        return aplicacionespasantiasList;
    }

    public void setAplicacionespasantiasList(List<Aplicacionespasantias> aplicacionespasantiasList) {
        this.aplicacionespasantiasList = aplicacionespasantiasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcurriculums != null ? idcurriculums.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curriculums)) {
            return false;
        }
        Curriculums other = (Curriculums) object;
        if ((this.idcurriculums == null && other.idcurriculums != null) || (this.idcurriculums != null && !this.idcurriculums.equals(other.idcurriculums))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Curriculums[ idcurriculums=" + idcurriculums + " ]";
    }
    
}
