package ru.farhodhaknazarov.newsapi.repo.entities

class Channel(
    var id: String,
    var name: String,
    var description: String,
    var url: String,
    var category: String,
    var language: String,
    var country: String
)