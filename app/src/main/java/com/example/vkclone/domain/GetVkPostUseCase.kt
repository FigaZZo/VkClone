package com.example.vkclone.domain

class GetVkPostUseCase(val repository: VkRepository) {
    operator fun invoke() = repository.getVkPosts()
}