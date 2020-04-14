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

package ec.animal.adoption.service;

import ec.animal.adoption.client.MediaStorageClient;
import ec.animal.adoption.domain.Animal;
import ec.animal.adoption.domain.media.ImagePicture;
import ec.animal.adoption.domain.media.LinkPicture;
import ec.animal.adoption.domain.media.PictureType;
import ec.animal.adoption.exception.EntityNotFoundException;
import ec.animal.adoption.exception.InvalidPictureException;
import ec.animal.adoption.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PictureService {

    private final MediaStorageClient mediaStorageClient;
    private final AnimalRepository animalRepository;

    @Autowired
    public PictureService(
            final MediaStorageClient mediaStorageClient,
            final AnimalRepository animalRepository
    ) {
        this.mediaStorageClient = mediaStorageClient;
        this.animalRepository = animalRepository;
    }

    public LinkPicture createPrimaryPicture(final ImagePicture imagePicture) {
        if (!PictureType.PRIMARY.equals(imagePicture.getPictureType()) || !imagePicture.isValid()) {
            throw new InvalidPictureException();
        }

        Animal animal = animalRepository.getBy(imagePicture.getAnimalUuid());
        LinkPicture linkPicture = mediaStorageClient.save(imagePicture);
        animal.setPrimaryLinkPicture(linkPicture);

        return animalRepository.save(animal).getPrimaryLinkPicture();
    }

    public LinkPicture getBy(final UUID animalUuid) {
        Animal animal = animalRepository.getBy(animalUuid);

        if (animal.getPrimaryLinkPicture() == null) {
            throw new EntityNotFoundException();
        }

        return animal.getPrimaryLinkPicture();
    }
}