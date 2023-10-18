package com.apricity.libnet

import com.apricity.libnet.service.GithubService

object GithubUtil {
    fun user(){
        val service : GithubService = HttpUtil.createService(GithubService::class.java)
        service.listRepos("tancongcong_123").execute()
    }
}