/*
    Copyright © 2018 Luisa Emme

    This file is part of Adoption Service in the Rescued Animals Platform.

    Adoption Service is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Adoption Service is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with Adoption Service.  If not, see <https://www.gnu.org/licenses/>.
 */

package ec.animal.adoption.repository.jpa.model;

import ec.animal.adoption.domain.story.Story;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "story")
@SuppressWarnings("PMD.ShortVariable")
public class JpaStory implements Serializable {

    private transient static final long serialVersionUID = -242532859161428810L;

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private JpaAnimal jpaAnimal;

    private LocalDateTime registrationDate;

    @NotNull
    private String text;

    private JpaStory() {
        // Required by jpa
    }

    public JpaStory(final Story story, final JpaAnimal jpaAnimal) {
        this();
        this.setId(story.getIdentifier());
        this.jpaAnimal = jpaAnimal;
        this.setRegistrationDate(story.getRegistrationDate());
        this.text = story.getText();
    }

    private void setId(final UUID id) {
        this.id = id == null ? UUID.randomUUID() : id;
    }

    private void setRegistrationDate(final LocalDateTime registrationDate) {
        this.registrationDate = registrationDate == null ? LocalDateTime.now() : registrationDate;
    }

    public Story toStory() {
        return new Story(this.id, this.registrationDate, this.text);
    }

    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpaStory jpaStory = (JpaStory) o;

        return id != null ? id.equals(jpaStory.id) : jpaStory.id == null;
    }

    @Override
    @SuppressWarnings("PMD")
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
