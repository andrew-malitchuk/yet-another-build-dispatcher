package dev.yabd.plugin.internal.usecase.base

import dev.yabd.plugin.common.model.base.NetModel

/**
 * Abstract base class for defining use cases.
 */
abstract class UseCase {
    /**
     * Executes the use case.
     * @return The result of the use case operation, typically a NetModel.
     */
    abstract operator fun invoke(): NetModel?
}
