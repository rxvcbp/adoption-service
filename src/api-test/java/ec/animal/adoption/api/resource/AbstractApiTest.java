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

package ec.animal.adoption.api.resource;

import ec.animal.adoption.builders.AnimalBuilder;
import ec.animal.adoption.domain.animal.Animal;
import ec.animal.adoption.domain.state.LookingForHuman;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.System.getenv;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractApiTest {

    static final String CREATE_ANIMAL_URL = "/adoption/animals?organizationId={organizationId}";
    static final String GET_ANIMAL_URL = "/adoption/animals/{uuid}";
    static final String GET_ANIMALS_URL = "/adoption/animals";
    static final String CHARACTERISTICS_URL = "/adoption/animals/{animalUuid}/characteristics";
    static final String PICTURES_URL = "/adoption/animals/{animalUuid}/pictures";
    static final String STORY_URL = "/adoption/animals/{animalUuid}/story";

    static WebTestClient webTestClient;

    @BeforeAll
    public static void setUpClass() {
        String host = getenv("ADOPTION_SERVICE_URL");
        String jwtAccessToken = getenv("JWT_ACCESS_TOKEN");
        webTestClient = WebTestClient.bindToServer()
                                     .defaultHeader("Authorization", "Bearer " + jwtAccessToken)
                                     .baseUrl(host)
                                     .responseTimeout(Duration.ofSeconds(10))
                                     .build();
    }

    Animal createRandomAnimalWithDefaultLookingForHumanState() {
        return webTestClient.post()
                            .uri(CREATE_ANIMAL_URL, AnimalBuilder.DEFAULT_ORGANIZATION_UUID)
                            .bodyValue(AnimalBuilder.random().withState(new LookingForHuman(LocalDateTime.now())).build())
                            .exchange()
                            .expectStatus()
                            .isCreated()
                            .expectBody(Animal.class)
                            .returnResult()
                            .getResponseBody();
    }

    void createAnimal(final Animal animal) {
        webTestClient.post()
                     .uri(CREATE_ANIMAL_URL, AnimalBuilder.DEFAULT_ORGANIZATION_UUID)
                     .bodyValue(animal)
                     .exchange()
                     .expectStatus()
                     .isCreated()
                     .expectBody(Animal.class)
                     .returnResult()
                     .getResponseBody();
    }
}