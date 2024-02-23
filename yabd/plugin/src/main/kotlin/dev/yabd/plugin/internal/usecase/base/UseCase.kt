package dev.yabd.plugin.internal.usecase.base

import dev.yabd.plugin.common.model.base.NetModel

abstract class UseCase {
    abstract operator fun invoke(): NetModel?
}
