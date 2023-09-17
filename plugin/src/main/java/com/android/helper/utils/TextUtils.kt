object TextUtils {

    fun isEmpty(content: String?): Boolean {
        return if (content == null) {
            true
        } else {
            content.length == 0
        }
    }
}