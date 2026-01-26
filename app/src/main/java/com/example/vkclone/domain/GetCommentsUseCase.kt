package com.example.vkclone.domain

class GetCommentsUseCase(val repository: VkRepository) {
    operator fun invoke() = repository.getComments()
}