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

package ec.animal.adoption.domain.media;

import ec.animal.adoption.domain.validators.ValidImage;

import javax.validation.constraints.Min;
import java.util.Arrays;

@ValidImage
public class Image {
    private final String extension;
    private final byte[] content;

    @Min(value = 1, message = "Image size in bytes can't be zero")
    private final long sizeInBytes;

    public Image(String extension, byte[] content, long sizeInBytes) {

        this.extension = extension;
        this.content = content;
        this.sizeInBytes = sizeInBytes;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getContent() {
        return content;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (sizeInBytes != image.sizeInBytes) return false;
        if (extension != null ? !extension.equals(image.extension) : image.extension != null) return false;
        return Arrays.equals(content, image.content);
    }

    @Override
    public int hashCode() {
        int result = extension != null ? extension.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (int) (sizeInBytes ^ (sizeInBytes >>> 32));
        return result;
    }
}