package seedu.address.model.image;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Represents an Image in FomoFoto
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Image {

    // Data fields
    private String name;
    private int height;
    private int width;
    private String encodedImage;

    /**
     * Every field must be present and not null.
     */
    public Image(String url) {
        requireAllNonNull(url);
        try {
            File file = new File(url);
            BufferedImage buffer = ImageIO.read(file);
            this.name = file.getName();
            this.height = buffer.getHeight();
            this.width = buffer.getWidth();

            // Encode image.
            this.encodedImage = encode(url);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Image(String name, int height, int width, String encodedImage) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.encodedImage = encodedImage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public String encode(String url) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(url));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public void printMetadata() throws IOException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(new BufferedInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(encodedImage))), true);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }
}