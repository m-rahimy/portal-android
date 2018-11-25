package ir.mrahimy.ingress.portal.app.model

/**
 * Created by vincent on 11/23/18.
 */
data class Portal(
        var id: String?,
        var title: String?,
        var description: String?,
        var likes: List<IngressUser>?,
        var reported: List<IngressUser>?,
        var imageUrls: List<String>?,
        var location: Pair<Float, Float>
)

data class IngressUser(
        var name: String?,
        var email: String?
)