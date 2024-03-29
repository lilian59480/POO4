/*
 * POO4 Project
 * Copyright (C) 2019
 * Lilian Petitpas, Thomas Ternisien, Thibaut Fenain, Corentin Apolinario
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 * Client model representation.
 *
 * @author Corentin
 */
@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Integer id;

    /**
     * Demand of the client.
     */
    private int demande;

    /**
     * List of Client's Emplacements.
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @CascadeOnDelete
    private List<Emplacement> emplacements;

    /**
     * Current position.
     */
    private int position;

    /**
     * Vehicule linked.
     */
    @ManyToOne
    private Vehicule vehicule;

    /**
     * Client constructor, with a null demand.
     */
    public Client() {
        this(0);
    }

    /**
     * Client constructor with a specific demand.
     *
     * @param demande Client's demand.
     */
    public Client(int demande) {
        this.position = -1;
        this.emplacements = new LinkedList<>();
        this.demande = demande;
        this.vehicule = null;
    }

    /**
     * Add a emplacement and set Emplacement client to itself.
     *
     * @param e Emplacement to add
     * @return True if we can add this emplacement.
     */
    public boolean addEmplacement(Emplacement e) {
        if (e == null || this.emplacements.contains(e)) {
            return false;
        }
        e.setClient(this);
        return this.emplacements.add(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (this.demande != other.getDemande()) {
            return false;
        }
        return Objects.equals(this.emplacements, other.getEmplacements());
    }

    /**
     * Get demand.
     *
     * @return The demand.
     */
    public int getDemande() {
        return this.demande;
    }

    /**
     * Get position.
     *
     * @return The position.
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Set new position.
     *
     * @param position The position.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get emplacement list.
     *
     * @return The list of emplacements.
     */
    public List<Emplacement> getEmplacements() {
        return this.emplacements;
    }

    /**
     * Get id Client.
     *
     * @return id Client
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Get current vehicule.
     *
     * @return The vehicule.
     */
    public Vehicule getVehicule() {
        return this.vehicule;
    }

    /**
     * Set new vehicule.
     *
     * @param vehicule The vehicule.
     */
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.demande;
        hash = 37 * hash + Objects.hashCode(this.emplacements);
        return hash;
    }

    @Override
    public String toString() {
        return "Client{" + "demande=" + demande + ", emplacements=" + emplacements + ", position=" + position + ", vehicule=" + vehicule + '}';
    }

    /**
     * Clear this instance.
     */
    public void clear() {
        this.position = -1;
        this.vehicule = null;
    }

    /**
     * Check if a client is valid.
     *
     * Check if a vehicule is set to this client.
     *
     * @return True if this client is valid, false otherwise
     */
    public boolean check() {
        return this.vehicule != null;
    }

}
