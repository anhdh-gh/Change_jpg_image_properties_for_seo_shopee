package utils;

import java.io.*;
import org.apache.commons.imaging.*;
import org.apache.commons.imaging.common.*;
import org.apache.commons.imaging.formats.jpeg.*;
import org.apache.commons.imaging.formats.jpeg.exif.*;
import org.apache.commons.imaging.formats.tiff.*;
import org.apache.commons.imaging.formats.tiff.constants.*;
import org.apache.commons.imaging.formats.tiff.write.*;
import org.apache.commons.io.*;

public class JpgImageFileUtils {

    public static void changeExifMetadata(File inputJpegImageFile, File outputJpegImageFile) throws IOException, ImageReadException, ImageWriteException {
        OutputStream os = null;
        try {
            TiffOutputSet outputSet = null;

            // note that metadata might be null if no metadata is found.
            final ImageMetadata metadata = Imaging.getMetadata(inputJpegImageFile);
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            if (null != jpegMetadata) {
                // note that exif might be null if no Exif metadata is found.
                final TiffImageMetadata exif = jpegMetadata.getExif();

                if (null != exif) {
                    // TiffImageMetadata class is immutable (read-only).
                    // TiffOutputSet class represents the Exif data to write.
                    //
                    // Usually, we want to update existing Exif metadata by
                    // changing
                    // the values of a few fields, or adding a field.
                    // In these cases, it is easiest to use getOutputSet() to
                    // start with a "copy" of the fields read from the image.
                    outputSet = exif.getOutputSet();
                }
            }

            // if file does not contain any exif metadata, we create an empty
            // set of exif metadata. Otherwise, we keep all of the other
            // existing tags.
            if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }

            {
                // Example of how to add a field/tag to the output set.
                //
                // Note that you should first remove the field/tag if it already
                // exists in this directory, or you may end up with duplicate
                // tags. See above.
                //
                // Certain fields/tags are expected in certain Exif directories;
                // Others can occur in more than one directory (and often have a
                // different meaning in different directories).
                //
                // TagInfo constants often contain a description of what
                // directories are associated with a given tag.
                //
                final TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
                // make sure to remove old value if present (this method will
                // not fail if the tag does not exist).
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_APERTURE_VALUE, new RationalNumber(3, 10));
            }

            {
                // Example of how to add/update GPS info to output set.

                // New York City
                final double longitude = -74.0; // 74 degrees W (in Degrees East)
                final double latitude = 40 + 43 / 60.0; // 40 degrees N (in Degrees
                // North)

                outputSet.setGPSInDegrees(longitude, latitude);
            }

            // ----------------------------------------------------------------------------------
            final TiffOutputDirectory exifDirectory = outputSet.getOrCreateRootDirectory();

            exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
            exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPTITLE, fomatTitleSubject(inputJpegImageFile.getName()));

            exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT);
            exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT, fomatTitleSubject(inputJpegImageFile.getName()));

            exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_RATING);
            exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_RATING, new Short("5"));

            exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS);
            exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS, fomatTag(inputJpegImageFile.getName()));
            // ----------------------------------------------------------------------------------

            os = new FileOutputStream(outputJpegImageFile);
            os = new BufferedOutputStream(os);

            new ExifRewriter().updateExifMetadataLossless(inputJpegImageFile, os, outputSet);

        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    private static String fomatTitleSubject(String fileName) {
        String[] words = fileName.split("-");
        String res = words[0].split(" ")[1];
        for(int i = 1 ; i < words.length-1 ; i++) {
            res += "-" + words[i];
        }
        return res + "-" + words[words.length-1].substring(0, words[words.length-1].length() - 4);
    }
    
    private static String fomatTag(String fileName) {
        fileName = fomatTitleSubject(fileName);
        String[] words = fileName.split("-");
        String res = "#" + words[0];
        for(int i = 1 ; i < words.length ; i++) {
            res += "; #" + words[i];
        }        
        return res;
    }
}