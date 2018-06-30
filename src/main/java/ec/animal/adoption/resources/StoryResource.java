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

package ec.animal.adoption.resources;

import ec.animal.adoption.domain.Story;
import ec.animal.adoption.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/animals/{animalUuid}/story")
public class StoryResource {

    private final StoryService storyService;

    @Autowired
    public StoryResource(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Story create(@PathVariable("animalUuid") UUID animalUuid, @RequestBody @Valid Story story) {
        story.setAnimalUuid(animalUuid);
        return storyService.create(story);
    }

    @GetMapping
    public Story get(@PathVariable("animalUuid") UUID animalUuid) {
        return storyService.getBy(animalUuid);
    }
}
