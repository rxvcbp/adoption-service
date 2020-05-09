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

package ec.animal.adoption.api.model.animal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.animal.adoption.TestUtils;
import ec.animal.adoption.domain.animal.Animal;
import ec.animal.adoption.domain.animal.AnimalBuilder;
import ec.animal.adoption.domain.state.State;
import ec.animal.adoption.domain.state.StateName;
import ec.animal.adoption.domain.utils.TranslatorUtils;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateAnimalResponseTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = TestUtils.getObjectMapper();
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        ReflectionTestUtils.setField(TranslatorUtils.class, "messageSource", messageSource);
    }

    @Test
    void shouldBeSerializable() throws JsonProcessingException {
        Animal animal = AnimalBuilder.random().withState(State.adopted(null)).build();
        String expectedRegistrationDateAsJson = objectMapper.writeValueAsString(animal.getRegistrationDate());
        CreateAnimalResponse createAnimalResponse = CreateAnimalResponse.from(animal);

        String createAnimalResponseAsJson = objectMapper.writeValueAsString(createAnimalResponse);

        assertAll(
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"id\":\"%s\"", animal.getIdentifier()))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"registrationDate\":%s", expectedRegistrationDateAsJson))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"clinicalRecord\":\"%s\"", animal.getClinicalRecord()))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"name\":\"%s\"", animal.getName()))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"species\":\"%s\"", animal.getSpecies().toTranslatedName()))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"estimatedAge\":\"%s\"", animal.getEstimatedAge().toTranslatedName()))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"sex\":\"%s\"", animal.getSex().toTranslatedName()))),
                () -> assertTrue(createAnimalResponseAsJson.contains(String.format("\"state\":{\"name\":\"%s\"}", StateName.ADOPTED.toTranslatedName())))
        );
    }

    @Test
    void shouldBeDeSerializable() throws JsonProcessingException, JSONException {
        String notes = randomAlphabetic(10);
        Animal animal = AnimalBuilder.random().withState(State.unavailable(notes)).build();
        CreateAnimalResponse expectedCreateAnimalResponse = CreateAnimalResponse.from(animal);
        String createAnimalResponseAsJson = new JSONObject()
                .put("id", animal.getIdentifier())
                .put("registrationDate", animal.getRegistrationDate())
                .put("clinicalRecord", animal.getClinicalRecord())
                .put("name", animal.getName())
                .put("species", animal.getSpecies().toTranslatedName())
                .put("estimatedAge", animal.getEstimatedAge().toTranslatedName())
                .put("sex", animal.getSex().toTranslatedName())
                .put("state", new JSONObject()
                        .put("name", StateName.UNAVAILABLE.toTranslatedName())
                        .put("notes", notes)
                ).toString();

        CreateAnimalResponse createAnimalResponse = objectMapper.readValue(createAnimalResponseAsJson, CreateAnimalResponse.class);

        Assertions.assertThat(createAnimalResponse).usingRecursiveComparison().isEqualTo(expectedCreateAnimalResponse);
    }
}