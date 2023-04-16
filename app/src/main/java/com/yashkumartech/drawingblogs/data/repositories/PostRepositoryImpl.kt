package com.yashkumartech.drawingblogs.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.yashkumartech.drawingblogs.domain.repositories.PostRepository
import com.yashkumartech.drawingblogs.util.PostObject
import com.yashkumartech.drawingblogs.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl: PostRepository {
    override suspend fun getPosts(): Flow<Resource<List<PostObject>>> {
        return flow {
            emit(Resource.Success(
                listOf(
                    PostObject(
                        "https://images.wallpapersden.com/image/download/punishing-gray-raven-hd-android-gaming_bWpmaWyUmZqaraWkpJRmZW1lrWdoZmU.jpg",
                        "PGR",
                        "Random image from PGR",
                        false,
                        "10-04-2023"
                    ),
                    PostObject(
                        "https://th.bing.com/th/id/OIP.xeC34bv5LMRsUS9YGkmP7QHaNK?pid=ImgDet&rs=1",
                        "Vocaloid",
                        "Portrait image to check scrolling",
                        true,
                        "11-04-2023"
                    ),
                    PostObject(
                        "https://funart.pro/uploads/posts/2021-04/1618579960_52-funart_pro-p-oboi-fon-chistoe-nebo-anime-57.jpg",
                        "Vocaloid",
                        "Current wallpaper",
                        true,
                        "10-04-2023"
                    ),
                    PostObject(
                        "https://upload-os-bbs.hoyolab.com/upload/2022/09/08/e0d8de0f2467d01ea9b9700951a82a6b_4126946274307179043.png?x-oss-process=image/resize,s_1000/quality,q_80/auto-orient,0/interlace,1/format,png",
                        "HoyoLab",
                        "Random image from Hoyolab",
                        false,
                        "10-04-2023"
                    ),
                )
            )
            )
        }
    }

    override suspend fun like(user: FirebaseUser, post: Int): Resource<Boolean> {
        return Resource.Success(true)
    }
}