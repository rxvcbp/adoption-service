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

package ec.animal.adoption.domain.characteristics.temperaments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum Sociability {
    VERY_SOCIABLE("Very sociable"),
    SOCIABLE("Sociable"),
    NEITHER_SOCIABLE_NOR_SHY("Neither sociable nor shy"),
    SHY("Shy"),
    VERY_SHY("Very shy");

    private final String name;

    Sociability(final String name) {
        this.name = name;
    }

    @JsonCreator
    @SuppressWarnings({"PMD.UnusedPrivateMethod"})
    private static Sociability forValue(final String value) {
        try {
            return Sociability.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return Stream.of(Sociability.values())
                         .filter(e -> e.getName().equals(value))
                         .findFirst()
                         .orElseThrow(() -> ex);
        }
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
