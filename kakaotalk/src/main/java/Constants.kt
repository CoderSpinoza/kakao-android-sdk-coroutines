package com.kakao.sdk.kakaotalk

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 4. 28..
 */
object Constants {
    const val PROFILE_PATH = "/v1/api/talk/profile"
    const val MEMO_PATH = "/v2/api/talk/memo/send"
    const val MEMO_DEFAULT_PATH = "/v2/api/talk/memo/default/send"
    const val MEMO_SCRAP_PATH = "/v2/api/talk/memo/scrap/send"
    const val MESSAGE_PATH = "/v2/api/talk/message/send"
    const val CHATS_PATH = "/v1/api/talk/chat/list"

    const val V1_PLUS_FRIENDS_PATH = "/v1/api/talk/plusfriends"

    const val SECURE_RESOURCE = "secure_resource"
    const val NICKNAME = "nickName"
    const val PROFILE_IMAGE_URL = "profileImageURL"
    const val THUMBNAIL_URL = "thumbnailURL"
    const val COUNTRY_ISO = "countryISO"

    const val ELEMENTS = "elements"
    const val TOTAL_COUNT = "total_count"
    const val BEFORE_URL = "before_url"
    const val AFTER_URL = "after_url"

    const val FROM_ID = "from_id"
    const val LIMIT = "limit"
    const val ORDER = "order"
    const val FILTER = "filter"

    const val ID = "id"
    const val TITLE = "title"
    const val IMAGE_URL = "image_url"
    const val MEMBER_COUNT = "member_count"
    const val DISPLAY_MEMBER_IMAGES = "display_member_images"
    const val CHAT_TYPE = "chat_type"

    const val RECEIVER_ID_TYPE = "receiver_id_type"
    const val RECEIVER_ID = "receiver_id"
    const val TEMPLATE_ID = "template_id"
    const val TEMPLATE_ARGS = "template_args"
    const val TEMPLATE_OBJECT = "template_object"
    const val REQUEST_URL = "request_url"

    const val PLUS_FRIEND_UUID = "plus_friend_uuid"
    const val PLUS_FRIEND_PUBLIC_ID = "plus_friend_public_id"
    const val PLUS_FRIEND_PUBLIC_IDS = "plus_friend_public_ids"
    const val RELATION = "relation"
    const val UPDATED_TIME = "updated_time"
}