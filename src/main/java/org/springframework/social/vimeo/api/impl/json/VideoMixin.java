package org.springframework.social.vimeo.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.vimeo.api.model.CreativeCommonLicenseType;
import org.springframework.social.vimeo.api.model.Image;
import org.springframework.social.vimeo.api.model.Privacy;
import org.springframework.social.vimeo.api.model.ProPerson;

import java.util.Date;
import java.util.List;


/**
 * User: soldier
 * Date: 2/3/12
 * Time: 4:11 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class VideoMixin {

    @JsonCreator
    VideoMixin(
            @JsonDeserialize(using = BooleanJsonDeserializer.class)
            @JsonProperty("allow_adds")
            Boolean allowAdds,
            @JsonProperty("embed_privacy")
            String embedPrivacy,
            @JsonProperty("id")
            Integer id,
            @JsonDeserialize(using = BooleanJsonDeserializer.class)
            @JsonProperty("is_hd")
            Boolean hd,
            @JsonDeserialize(using = BooleanJsonDeserializer.class)
            @JsonProperty("is_transcoding")
            Boolean transcoding,
            @JsonProperty("license")
            @JsonDeserialize(using = CreativeCommonLicenseJsonDeserializer.class)
            CreativeCommonLicenseType license,
            @JsonDeserialize(using = PrivacyJsonDeserilizer.class)
            @JsonProperty("privacy")
            Privacy privacy,
            @JsonProperty("title")
            String title,
            @JsonProperty("description")
            String description,
            @JsonProperty("upload_date")
            Date uploadDate,
            @JsonProperty("modified_date")
            Date modifiedDate,
            @JsonProperty("number_of_likes")
            Integer numberOfLikes,
            @JsonProperty("number_of_plays")
            Integer numberOfPlays,
            @JsonProperty("number_of_comments")
            Integer numberOfComments,
            @JsonProperty("width")
            Integer width,
            @JsonProperty("height")
            Integer height,
            @JsonProperty("duration")
            Integer duration,
            @JsonProperty("owner")
            ProPerson owner) {
    }

    @JsonProperty("thumbnails")
    @JsonDeserialize(using = ThumbnailsJsonDeserializer.class)
    List<Image> thumbnails;

}
