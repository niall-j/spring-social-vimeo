package org.springframework.social.vimeo.api.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.vimeo.*;
import org.springframework.social.vimeo.api.ChannelOperations;
import org.springframework.social.vimeo.api.model.*;
import org.springframework.web.client.RestTemplate;

/**
 * User: soldier
 * Date: 2/8/12
 * Time: 7:51 PM
 */
class ChannelTemplate extends AbstractVimeoTemplate implements ChannelOperations {

    private final static VimeoMethod ADD_VIDEO = new VimeoMethodImpl("vimeo.channels.addVideo") {
        {
            add(1, VideoNotFoundException.class);
            add(2, UserNotModeratorException.class);
            add(3, OperationNotPermittedException.class);
        }
    };
    private final static VimeoMethod REMOVE_VIDEO = new VimeoMethodImpl("vimeo.channels.removeVideo") {
        {
            add(1, VideoNotFoundException.class);
            add(2, UserNotModeratorException.class);
            add(3, VideoNotInChannelException.class);
        }
    };
    private final static VimeoMethod MODERATED = new VimeoMethodImpl("vimeo.channels.getModerated", "channels") {
        {
            add(1, UserNotFoundException.class);
        }
    };
    private final static VimeoMethod MODERATORS = new VimeoMethodImpl("vimeo.channels.getModerators", "moderators") {
        {
            add(1, ChannelNotFoundException.class);
        }
    };
    private final static VimeoMethod VIDEOS = new VimeoMethodImpl("vimeo.channels.getVideos", "videos") {
        {
            add(1, ChannelNotFoundException.class);
        }
    };
    private final static VimeoMethod UNSUBSCRIBE = new VimeoMethodImpl("vimeo.channels.unsubscribe") {
        {
            add(1, ChannelNotFoundException.class);
            add(2, NotSubscribedException.class);
        }
    };
    private final static VimeoMethod SUBSCRIBE = new VimeoMethodImpl("vimeo.channels.subscribe") {
        {
            add(1, ChannelNotFoundException.class);
            add(2, AlreadySubscribedException.class);
        }
    };
    private final static VimeoMethod SUBSCRIBERS = new VimeoMethodImpl("vimeo.channels.getSubscribers", "subscribers") {
        {
            add(1, ChannelNotFoundException.class);
        }
    };
    private final static VimeoMethod ALL = new VimeoMethodImpl("vimeo.channels.getAll", "channels") {
        {
            add(1, UserNotFoundException.class);
        }
    };
    private final static VimeoMethod INFO = new VimeoMethodImpl("vimeo.channels.getInfo", "channel") {
        {
            add(1, ChannelNotFoundException.class);
        }
    };

    public ChannelTemplate(RestTemplate restTemplate, ObjectMapper mapper, Permission permission) {
        super(restTemplate, mapper, permission);
    }

    @Override
    public void addVideo(String channelId, Long videoId) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        params.add("video_id", videoId);
        doMethod(ADD_VIDEO, params.build());
    }

    @Override
    public ChannelInfos moderated(String userId, Integer page, Integer perPage, VideoCollectionSortMethod sortBy) {
        ParamsBuilder params = new ParamsBuilder();
        params.addUser(userId);
        params.addIfNotNull("page", page);
        params.addIfNotNull("per_page", perPage, 50);
        params.addIfNotNull("sort", sortBy);
        return getObject(MODERATED, params.build(), ChannelInfos.class);
    }

    @Override
    public Videos videos(String channelId, Integer page, Integer perPage) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        params.add("full_response", "1");
        params.addIfNotNull("page", page);
        params.addIfNotNull("per_page", perPage, 50);
        return getObject(VIDEOS, params.build(), Videos.class);
    }

    @Override
    public void unsubscribe(String channelId) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        doMethod(UNSUBSCRIBE, params.build());
    }

    @Override
    public Channels all(String userId, Integer page, Integer perPage, VideoCollectionSortMethod sortBy) {
        ParamsBuilder params = new ParamsBuilder();
        params.addIfNotNull("user_id", userId);
        params.addIfNotNull("page", page);
        params.addIfNotNull("per_page", perPage, 50);
        params.addIfNotNull("sort", sortBy);
        return getObject(ALL, params.build(), Channels.class);
    }

    @Override
    public void subscribe(String channelId) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        doMethod(SUBSCRIBE, params.build());
    }

    @Override
    public People subscribers(String channelId, Integer page, Integer perPage) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        params.addIfNotNull("page", page);
        params.addIfNotNull("per_page", perPage, 50);
        return getObject(SUBSCRIBERS, params.build(), People.class);
    }

    @Override
    public ChannelInfo info(String channelId) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        return getObject(INFO, params.build(), ChannelInfo.class);
    }

    @Override
    public void removeVideo(String channelId, Long videoId) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        params.add("video_id", videoId);
        doMethod(REMOVE_VIDEO, params.build());
    }

    @Override
    public Moderators moderators(String channelId, Integer page, Integer perPage) {
        ParamsBuilder params = new ParamsBuilder();
        params.add("channel_id", channelId);
        params.addIfNotNull("page", page);
        params.addIfNotNull("per_page", perPage, 50);
        return getObject(MODERATORS, params.build(), Moderators.class);
    }
}
