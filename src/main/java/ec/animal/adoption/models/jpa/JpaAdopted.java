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

package ec.animal.adoption.models.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.animal.adoption.domain.state.Adopted;
import ec.animal.adoption.domain.state.State;

import java.time.LocalDateTime;

public class JpaAdopted implements JpaStateable {

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("adoptionFormId")
    private String adoptionFormId;

    private JpaAdopted() {
        // Required for json serialization and deserialization
    }

    public JpaAdopted(Adopted adopted) {
        this();
        this.date = adopted.getDate();
        this.adoptionFormId = adopted.getAdoptionFormId();
    }

    @Override
    public State toState() {
        return new Adopted(date, adoptionFormId);
    }
}