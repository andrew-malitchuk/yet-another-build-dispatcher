package dev.yabd.plugin.internal.usecase.jira.model.request

import dev.yabd.plugin.common.model.base.NetModel

data class JiraAuthorizationNetModel(
    val email: String,
    val token: String,
) : NetModel
