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

package ec.animal.adoption.domain;

import ec.animal.adoption.dtos.AnimalDto;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static ec.animal.adoption.TestUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnimalsTest {

    @Test
    public void shouldReturnListOfAnimalsDto() {
        List<AnimalDto> expectedListOfAnimalDtos = newArrayList(
                getRandomAnimalDto(), getRandomAnimalDto(), getRandomAnimalDto(), getRandomAnimalDto()
        );
        Animals animals = new Animals(expectedListOfAnimalDtos);

        assertThat(animals.getListOfAnimals(), is(expectedListOfAnimalDtos));
    }

    private AnimalDto getRandomAnimalDto() {
        return new AnimalDto(
                UUID.randomUUID(),
                randomAlphabetic(10),
                getRandomSpecies(),
                getRandomEstimatedAge(),
                getRandomSex()
        );
    }
}