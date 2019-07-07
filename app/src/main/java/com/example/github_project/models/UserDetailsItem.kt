package com.example.github_project.models

class UserDetailsItem(_type: Int, _owner: Owner?, _repository: Repository?) {

    var type: Int? = _type

    var owner: Owner? = _owner

    var repository: Repository? = _repository
}