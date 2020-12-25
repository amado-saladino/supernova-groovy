package models

import groovy.transform.Canonical

@Canonical
class User {
    String id
    String name
    int age
    String city
}
