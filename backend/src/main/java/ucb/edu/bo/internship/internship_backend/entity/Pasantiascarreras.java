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
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "pasantiascarreras")
@NamedQueries({
    @NamedQuery(name = "Pasantiascarreras.findAll", query = "SELECT p FROM Pasantiascarreras p"),
    @NamedQuery(name = "Pasantiascarreras.findByIdpasantiascarreras", query = "SELECT p FROM Pasantiascarreras p WHERE p.idpasantiascarreras = :idpasantiascarreras")})
public class Pasantiascarreras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasantiascarreras")
    private Integer idpasantiascarreras;
    @JoinColumn(name = "carreras_idcarreras", referencedColumnName = "idcarreras")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Carreras carrerasIdcarreras;
    @JoinColumn(name = "pasantias_idpasantias", referencedColumnName = "idpasantias")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pasantias pasantiasIdpasantias;

    public Pasantiascarreras() {
    }

    public Pasantiascarreras(Integer idpasantiascarreras) {
        this.idpasantiascarreras = idpasantiascarreras;
    }

    public Integer getIdpasantiascarreras() {
        return idpasantiascarreras;
    }

    public void setIdpasantiascarreras(Integer idpasantiascarreras) {
        this.idpasantiascarreras = idpasantiascarreras;
    }

    public Carreras getCarrerasIdcarreras() {
        return carrerasIdcarreras;
    }

    public void setCarrerasIdcarreras(Carreras carrerasIdcarreras) {
        this.carrerasIdcarreras = carrerasIdcarreras;
    }

    public Pasantias getPasantiasIdpasantias() {
        return pasantiasIdpasantias;
    }

    public void setPasantiasIdpasantias(Pasantias pasantiasIdpasantias) {
        this.pasantiasIdpasantias = pasantiasIdpasantias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasantiascarreras != null ? idpasantiascarreras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasantiascarreras)) {
            return false;
        }
        Pasantiascarreras other = (Pasantiascarreras) object;
        if ((this.idpasantiascarreras == null && other.idpasantiascarreras != null) || (this.idpasantiascarreras != null && !this.idpasantiascarreras.equals(other.idpasantiascarreras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Pasantiascarreras[ idpasantiascarreras=" + idpasantiascarreras + " ]";
    }
    
}
