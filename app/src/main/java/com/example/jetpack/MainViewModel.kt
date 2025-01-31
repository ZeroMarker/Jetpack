package com.example.jetpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel: ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)
    val persons = searchText
        .combine(_persons) { text, persons ->
            if(text.isBlank()) {
                persons
            } else {
                persons.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

data class Person(
    val firstName: String,
    val lastName: String
) {
    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName$lastName",
            "${firstName.first()} ${lastName.first()}"
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allPersons = listOf(
    Person(
        firstName = "Mark",
        lastName = "Chen"
    ),
    Person(
        firstName = "Gin",
        lastName = "Chen"
    ),
    Person(
        firstName = "Taylor",
        lastName = "Swift"
    ),
    Person(
        firstName = "Linus",
        lastName = "Zox"
    ),
    Person(
        firstName = "Skylar",
        lastName = "Vox"
    ),
    Person(
        firstName = "あいり",
        lastName = "清水"
    ),
    Person(
        firstName = "紗梨",
        lastName = "香坂"
    )
)